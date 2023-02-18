package Grammar.AST.Expr;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class BinaryOperationNode extends Node.ExprNode {
    private final ExprNode left;
    private final ExprNode right;
    private final String operator;
    public BinaryOperationNode(ExprNode left, String operator, ExprNode right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public long eval(Game game) {
        long leftVal = left.eval(game);
        long rightVal = right.eval(game);
        return switch (operator) {
            case "+" -> leftVal + rightVal;
            case "-" -> leftVal - rightVal;
            case "*" -> leftVal * rightVal;
            case "/" -> leftVal / rightVal;
            case "%" -> leftVal % rightVal;
            case "^" -> (long) Math.pow(leftVal, rightVal);
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
    }

    @Override
    public String toString(){
        return left.toString() + " " + operator + " " + right.toString();
    }
}
