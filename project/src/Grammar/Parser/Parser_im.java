package Grammar.Parser;

import Grammar.AST.*;
import Grammar.AST.Eval.*;
import Grammar.AST.Expr.*;
import Grammar.Tokenizer.Tokenizer;

import java.util.*;

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
    Map<String, Long> mem;
    public Parser_im(Tokenizer tkz) {
        this.tkz = tkz;
        mem = new HashMap<>();
    }
    @Override
    public Node.EvalNode parse() {
        Node.EvalNode node = parsePlan();
        if(tkz.hasNextToken()) throw new RuntimeException("token is not null : " + tkz.peek());
        return node;
    }

    private Node.EvalNode parsePlan() {
        Node.EvalNode current = parseStatement();
        current.next = lookNext();
        return current;
    }

    private Node.EvalNode lookNext() {
        //I don't know to do this
        return null;
    }

    private Node.EvalNode parseStatement() {
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
    
    private Node.EvalNode parseBlockStatement() {
        tkz.consume("{");
        List<Node> blockNodes = findAllNodes();
        tkz.consume("}");
        return new BlockNode(blockNodes);
    }

    private List<Node> findAllNodes() {
        List<Node> nodes = new ArrayList<>();
        while(tkz.hasNextToken()){
            nodes.add(parseStatement());
        }
        return nodes;
    }

    private Node.EvalNode parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        Node.ExprNode expr = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Node.EvalNode thenNode = parseStatement();
        tkz.consume("else");
        Node.EvalNode elseNode = parseStatement();
        return new IfNode( expr, thenNode, elseNode );
    }
    
    private Node.EvalNode parseWhileStatement() {
        tkz.consume("while");
        tkz.consume("(");
        Node.ExprNode expr = parseExpression();
        tkz.consume(")");
        tkz.consume("{");
        Node.EvalNode body = parseStatement();
        tkz.consume("}");
        return new WhileNode( expr, body );
    }

    private Node.EvalNode parseCommand() {
        if(tkz.peek("done") || tkz.peek("relocate") || tkz.peek("move")
                || tkz.peek("invest") || tkz.peek("collect") || tkz.peek("shoot")){
            return parseActionCommand();
        }
        return parseAssignmentStatement();
    }

    private Node.EvalNode parseAssignmentStatement() {
        String identifier = tkz.consume();
        tkz.consume("=");
        Node.ExprNode expr = parseExpression();
        return new AssignmentNode( identifier, expr );
    }

    private Node.EvalNode parseActionCommand() {
        String action = tkz.consume();
        return switch (action) {
            case "done" -> new DoneNode();
            case "relocate" -> new RelocateNode();
            case "move" -> parseMoveCommand();
            case "invest" -> parseInvestCommand();
            case "collect" -> parseCollectCommand();
            case "shoot" -> parseShootCommand();
            default -> throw new RuntimeException("unknown command: " + tkz.peek());
        };
    }

    private Node.EvalNode parseMoveCommand() {
        tkz.consume();
        String direction = parseDirection();
        return new MoveNode( direction );
    }

    private String parseDirection() {
        String direction = tkz.consume();
        return switch (direction) {
            case "up","down","upleft","upright","downleft","downright" -> direction;
            default -> throw new RuntimeException("unknown direction: " + tkz.peek());
        };
    }

    private Node.EvalNode parseInvestCommand() {
        Node.ExprNode expr = parseExpression();
        return new InvestNode( expr );
    }

    private Node.EvalNode parseCollectCommand() {
        Node.ExprNode expr = parseExpression();
        return new CollectNode( expr );
    }

    private Node.EvalNode parseShootCommand() {
        String direction = parseDirection();
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
            String direction = parseDirection();
            return new NearbyNode(direction);
        }else throw new RuntimeException("unknown info expression: " + tkz.peek());
    }

}
