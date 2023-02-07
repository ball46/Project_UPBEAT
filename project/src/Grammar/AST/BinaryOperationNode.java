package Grammar.AST;

import java.util.Map;

public class BinaryOperationNode extends ExprNode {
    private final ExprNode left;
    private final ExprNode right;
    private final String operator;
    public BinaryOperationNode(ExprNode left, ExprNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public long eval(Map<String, Long> mem) {
        long leftVal = left.eval(mem);
        long rightVal = right.eval(mem);
        return switch (operator) {
            case "+" -> leftVal + rightVal;
            case "-" -> leftVal - rightVal;
            case "*" -> leftVal * rightVal;
            case "/" -> leftVal / rightVal;
            case "%" -> leftVal % rightVal;
            case "^" -> leftVal ^ rightVal;
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
    }
}
