package Grammar.AST.Expr;

import Grammar.AST.Node;

import java.util.Map;

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
    public long eval(Map<String, Long> memory) {
        long leftVal = left.eval(memory);
        long rightVal = right.eval(memory);
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
