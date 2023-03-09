package Grammar.Parser;

import Grammar.AST.*;
import Grammar.AST.State.*;
import Grammar.AST.Expr.*;
import Grammar.Tokenizer.Tokenizer;
import Type.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Parser_im implements Parser {
    Tokenizer tkz;
    HashSet<String> Command = new HashSet<>(Arrays.asList("done", "relocate", "move", "invest", "collect", "shoot"));
    HashSet<String> SpecialVariables = new HashSet<>(Arrays.asList("rows", "cols", "currow", "curcol", "budget", "deposit",
            "int", "maxdeposit", "random"));
    HashSet<String> NotUse = new HashSet<>(Arrays.asList("if", "while", "done", "relocate", "move", "invest", "shoot"
            , "up", "down", "upleft", "upright", "downleft", "downright", "if", "while", "then", "else", "opponent", "nearby",
            "rows", "cols", "currow", "curcol", "budget", "deposit", "int", "maxdeposit", "random"));

    public Parser_im(Tokenizer tkz) {
        if(!tkz.hasNextToken()) throw new ParserError.CommandIsNoInput();
        this.tkz = tkz;
    }
    @Override
    public List<Node.StateNode> parse() {
        List<Node.StateNode> nodes = parsePlan();
        if(tkz.hasNextToken()) throw new ParserError.CommandHasLeftoverToken(tkz.peek());
        return nodes;
    }

    private List<Node.StateNode> parsePlan() {
        List<Node.StateNode> plan = new ArrayList<>();
        plan.add(parseStatement());
        parseStatements(plan);
        return plan;
    }

    private void parseStatements(List<Node.StateNode> list) {
        while(tkz.hasNextToken() && !tkz.peek("}")) {
            list.add(parseStatement());
        }
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
        List<Node.StateNode> block = new ArrayList<>();
        tkz.consume("{");
        parseStatements(block);
        tkz.consume("}");
        return new BlockNode(block);
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
        if(NotUse.contains(identifier)){
            throw new ParserError.CommandHasNotUseVariable(identifier);
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
        if (tkz.peek("^")) {
            String op = tkz.consume();
            Node.ExprNode right = parseFactor();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private Node.ExprNode parsePower() {
        if(Character.isDigit(tkz.peek().charAt(0))){
            return new NumberNode( Long.parseLong(tkz.consume()) );
        }else if(tkz.peek("opponent") || tkz.peek("nearby")){
            return parseInfoExpression();
        }else if(SpecialVariables.contains(tkz.peek())){
            return new SpecialVariablesNode(tkz.consume());
        } else if(tkz.peek("(")){
            tkz.consume("(");
            Node.ExprNode expr = parseExpression();
            tkz.consume(")");
            return expr;
        }else if(Character.isAlphabetic(tkz.peek().charAt(0)) ){
            return new IdentifierNode( tkz.consume() );
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
