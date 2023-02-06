package Grammar.AST;

public class AssigmentNode implements Nodes{
    private final String identifier;
    private final Nodes expression;

    public AssigmentNode(String identifier, Nodes expression) {
        this.identifier = identifier;
        this.expression = expression;
    }
    public String getIdentifier() {
        return identifier;
    }
    public Nodes getExpression() {
        return expression;
    }
    @Override
    public void evaluate() {
        System.out.println("Assigning " + identifier + " to " + expression);
    }
}
