package Grammar.Parser;

import Grammar.AST.*;
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
    List<Node> allNodes;
    Map<String, Long> mem;
    public Parser_im(Tokenizer tkz) {
        this.tkz = tkz;
        allNodes = new ArrayList<>();
        mem = new HashMap<>();
    }
    @Override
    public Node parse() {
        Node node = parsePlan();
        if(tkz.hasNextToken()) throw new RuntimeException("token is not null : " + tkz.peek());
        return node;
    }

    private Node parsePlan() {
        return parseStatement();
    }

    private Node parseStatement() {
        if(tkz.peek("if")){
            return parseIfStatement();
        }else if(tkz.peek("while")){
//            Node whileNode = parseWhileStatement();
//            return whileNode;
        }else if(tkz.peek("{")){
//            Node blockNode = parseBlockStatement();
//            return blockNode;
        }else{
            return parseCommand();
        }
        return null;
    }
    
    private Node parseBlockStatement() {
        tkz.consume("{");
        Node blockNode = parseStatement();
        tkz.consume("}");
        return blockNode;
    }

    private Node parseIfStatement() {
        tkz.consume("if");
        tkz.consume("(");
        ExprNode expr = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Node thenNode = parseStatement();
        tkz.consume("else");
        Node elseNode = parseStatement();
        return new IfNode( expr, thenNode, elseNode );
    }
    
    private Node parseWhileStatement() {
        return null;
    }

    private Node parseCommand() {
        if(tkz.peek("done") || tkz.consume("relocate") || tkz.consume("move")
                || tkz.consume("invest") || tkz.consume("collect") || tkz.consume("shoot")){
            return parseActionCommand();
        }
        return parseAssignmentStatement();
    }

    private Node parseAssignmentStatement() {
        String identifier = tkz.consume();
        tkz.consume("=");
        ExprNode expr = parseExpression();
        return new AssignmentNode( identifier, expr );
    }

    private Node parseActionCommand() {
        if(tkz.peek("done")){
            tkz.consume();
            return new DoneNode();
        }else if(tkz.peek("relocate")){
            tkz.consume();
            return new RelocateNode();
        }else if(tkz.peek("move")){
            return parseMoveCommand();
        }else if(tkz.peek("invest")){
            return parseInvestCommand();
        }else if(tkz.peek("collect")){
            return parseCollectCommand();
        }else if(tkz.peek("shoot")){
            return parseShootCommand();
        }else throw new RuntimeException("unknown command: " + tkz.peek());
    }

    private Node parseMoveCommand() {
        tkz.consume();
        String direction = parseDirection();
        tkz.consume();
        return new MoveNode( direction );
    }

    private String parseDirection() {
        if(tkz.peek("up"))return "up";
        else if(tkz.peek("down"))return "down";
        else if(tkz.peek("upleft"))return "upleft";
        else if(tkz.peek("upright"))return "upright";
        else if(tkz.peek("downleft"))return "downleft";
        else if(tkz.peek("downright"))return "downright";
        else throw new RuntimeException("unknown direction: " + tkz.peek());
    }

    private Node parseInvestCommand() {
        String invest = tkz.consume();
        ExprNode expr = parseExpression();
        return new RegionNode( expr.eval(mem), invest );
    }

    private Node parseCollectCommand() {
        String collect = tkz.consume();
        ExprNode expr = parseExpression();
        return new RegionNode( expr.eval(mem), collect );
    }

    private Node parseShootCommand() {
        tkz.consume();
        String direction = parseDirection();
        ExprNode expr = parseExpression();
        return new AttackNode( direction, expr.eval(mem) );
    }

    private ExprNode parseExpression() {
        ExprNode left = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String op = tkz.consume();
            ExprNode right = parseTerm();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private ExprNode parseTerm() {
        ExprNode left = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String op = tkz.consume();
            ExprNode right = parseFactor();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private ExprNode parseFactor() {
        ExprNode left = parsePower();
        while (tkz.peek("^")) {
            String op = tkz.consume();
            ExprNode right = parsePower();
            left = new BinaryOperationNode( left, op, right );
        }
        return left;
    }

    private ExprNode parsePower() {
        if(Character.isDigit(tkz.peek().charAt(0))){
            return new NumberNode( Long.parseLong(tkz.consume()) );
        }else if(Character.isAlphabetic(tkz.peek().charAt(0))){
            return new IdentifierNode( tkz.consume() );
        }else if(tkz.peek("(")){
            tkz.consume("(");
            ExprNode expr = parseExpression();
            tkz.consume(")");
            return expr;
        }else if(tkz.peek("opponent") || tkz.peek("nearby")){
            return parseInfoExpression();
        }
        return null;
    }

    private ExprNode parseInfoExpression() {
        if(tkz.peek("oppnent")){
            tkz.consume();
            return new OpponentNode();
        }else if(tkz.peek("nearby")){
            tkz.consume();
            String direction = parseDirection();
            return new NearbyNode(direction);
        }else throw new RuntimeException("unknown info expression: " + tkz.peek());
    }

}
