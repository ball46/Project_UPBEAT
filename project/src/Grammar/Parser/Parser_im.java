package Grammar.Parser;

import Grammar.AST.*;
import Grammar.AST.State.*;
import Grammar.AST.Expr.*;
import Grammar.Tokenizer.Tokenizer;
import Type.Direction;
import java.util.Arrays;
import java.util.List;

public class Parser_im implements Parser {/*
    Plan → Statement+
    Statement → Command | BlockStatement | IfStatement | WhileStatement
    Command → AssignmentStatement | ActionCommand
    AssignmentStatement → <identifier> = Expression
    ActionCommand → done | relocate | MoveCommand | RegionCommand | AttackCommand
    MoveCommand → move Direction
    RegionCommand → invest Expression | collect Expression
    AttackCommand → shoot Direction Expression
    Direction → up | down | upleft | upright | downleft | downright
    BlockStatement → { Statement* }
    IfStatement → if ( Expression ) then Statement else Statement
    WhileStatement → while ( Expression ) Statement
    Expression → Expression + Term | Expression - Term | Term
    Term → Term * Factor | Term / Factor | Term % Factor | Factor
    Factor → Power ^ Factor | Power
    Power → <number> | <identifier> | ( Expression ) | InfoExpression
    InfoExpression → opponent | nearby Direction
*/
    Tokenizer tkz;
    List<String> Command = Arrays.asList("done", "relocate", "move", "invest", "collect", "shoot");
    List<String> SpecialVariables = Arrays.asList("if", "while", "done", "relocate", "move", "invest", "shoot"
            , "up", "down", "upleft", "upright", "downleft", "downright", "if", "while", "then", "else", "opponent", "nearby",
            "rows", "cols", "currow", "curcol", "budget", "deposit", "int", "maxdeposit", "random");

    public Parser_im(Tokenizer tkz) {
        if(!tkz.hasNextToken()) throw new ParserError.CommandIsNoInput();
        this.tkz = tkz;
    }
    @Override
    public Node.StateNode parse() {
        Node.StateNode nodes = parsePlan();
        if(tkz.hasNextToken()) throw new ParserError.CommandHasLeftoverToken(tkz.peek());
        return nodes;
    }

    private Node.StateNode parsePlan() {
        Node.StateNode current = parseStatement();
        current.nextState = parseStatements();
        return current;
    }

    private Node.StateNode parseStatements() {
        Node.StateNode node = null, subnode = null;
        while(tkz.hasNextToken() && !tkz.peek("}")) {
            Node.StateNode cur = parseStatement();
            if(node == null) node = cur;
            if(subnode != null) subnode.nextState = cur;
            subnode = cur;
        }
        return node;
    }

    private Node.StateNode parseStatement() {
        if(tkz.peek("if")){
            return parseIfStatement();
        }else if(tkz.peek("while")){
            return parseWhileStatement();
        }else if(tkz.peek("{")){
            return parseBlockStatement();
        }else{
            return parseCommand();
        }
    }
    
    private Node.StateNode parseBlockStatement() {
        tkz.consume("{");
        Node.StateNode blockNodes = parseStatements();
        tkz.consume("}");
        return blockNodes;
    }

    private Node.StateNode parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        Node.ExprNode expr = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Node.StateNode thenNode = parseStatement();
        tkz.consume("else");
        Node.StateNode elseNode = parseStatement();
        return new IfNode( expr, thenNode, elseNode );
    }
    
    private Node.StateNode parseWhileStatement() {
        tkz.consume("while");
        tkz.consume("(");
        Node.ExprNode expr = parseExpression();
        tkz.consume(")");
        Node.StateNode body = parseStatement();
        return new WhileNode( expr, body );
    }

    private Node.StateNode parseCommand() {
        if(Command.contains(tkz.peek())){
            return parseActionCommand();
        }
        return parseAssignmentStatement();
    }

    private Node.StateNode parseAssignmentStatement() {
        String identifier = tkz.consume();
        if(SpecialVariables.contains(identifier)){
            throw new ParserError.CommandHasSpecialVariable(identifier);
        }else {
            if (tkz.peek("=")) tkz.consume();
            else throw new ParserError.CommandNotFound("'='");
            Node.ExprNode expr = parseExpression();
            return new AssignmentNode(identifier, expr);
        }
    }

    private Node.StateNode parseActionCommand() {
        String action = tkz.consume();
        return switch (action) {
            case "done" -> new DoneNode();
            case "relocate" -> new RelocateNode();
            case "move" -> parseMoveCommand();
            case "invest" -> parseInvestCommand();
            case "collect" -> parseCollectCommand();
            case "shoot" -> parseShootCommand();
            default -> throw new ParserError.CommandIsUnknown(tkz.peek());
        };
    }

    private Node.StateNode parseMoveCommand() {
        Direction direction = parseDirection();
        return new MoveNode( direction );
    }

    private Direction parseDirection() {
        String direction = tkz.consume();
        return switch (direction) {
            case "up" -> Direction.Up;
            case "down" -> Direction.Down;
            case "upleft" -> Direction.UpLeft;
            case "upright" -> Direction.UpRight;
            case "downleft" -> Direction.DownLeft;
            case "downright" -> Direction.DownRight;
            default -> throw new ParserError.DirectionCommandIsUnknown(tkz.peek());
        };
    }

    private Node.StateNode parseInvestCommand() {
        Node.ExprNode expr = parseExpression();
        return new InvestNode( expr );
    }

    private Node.StateNode parseCollectCommand() {
        Node.ExprNode expr = parseExpression();
        return new CollectNode( expr );
    }

    private Node.StateNode parseShootCommand() {
        Direction direction = parseDirection();
        Node.ExprNode expr = parseExpression();
        return new AttackNode( direction, expr );
    }

    private Node.ExprNode parseExpression() {
        Node.ExprNode left = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String op = tkz.consume();
            Node.ExprNode right = parseTerm();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private Node.ExprNode parseTerm() {
        Node.ExprNode left = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String op = tkz.consume();
            Node.ExprNode right = parseFactor();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private Node.ExprNode parseFactor() {
        Node.ExprNode left = parsePower();
        while (tkz.peek("^")) {
            String op = tkz.consume();
            Node.ExprNode right = parsePower();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private Node.ExprNode parsePower() {
        if(Character.isDigit(tkz.peek().charAt(0))){
            return new NumberNode( Long.parseLong(tkz.consume()) );
        }else if(Character.isAlphabetic(tkz.peek().charAt(0))){
            return new IdentifierNode( tkz.consume() );
        }else if(tkz.peek("(")){
            tkz.consume("(");
            Node.ExprNode expr = parseExpression();
            tkz.consume(")");
            return expr;
        }else if(tkz.peek("opponent") || tkz.peek("nearby")){
            return parseInfoExpression();
        }
        return null;
    }

    private Node.ExprNode parseInfoExpression() {
        if(tkz.peek("opponent")){
            tkz.consume();
            return new OpponentNode();
        }else if(tkz.peek("nearby")){
            tkz.consume();
            Direction direction = parseDirection();
            return new NearbyNode(direction);
        }else throw new ParserError.InfoCommandIsUnknown(tkz.peek());
    }

}
