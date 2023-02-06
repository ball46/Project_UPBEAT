package Grammar.AST;

import java.util.Map;

public class BinaryOperationNode extends ExprNodes{
    private final ExprNodes left;
    private final ExprNodes right;
    private final String operator;
    public BinaryOperationNode(ExprNodes left, ExprNodes right, String operator) {
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
