package Grammar.AST;

public class AssigmentNode implements Node {
    private final String identifier;
    private final Node expression;

    public AssigmentNode(String identifier, Node expression) {
        this.identifier = identifier;
        this.expression = expression;
    }
    public String getIdentifier() {
        return identifier;
    }
    public Node getExpression() {
        return expression;
    }
    @Override
    public void evaluate() {
        System.out.println("Assigning " + identifier + " to " + expression);
    }
}
