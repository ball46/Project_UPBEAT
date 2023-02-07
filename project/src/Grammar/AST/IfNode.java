package Grammar.AST;

public class IfNode implements Node {
    private final Node condition;
    private final Node trueBranch;
    private final Node falseBranch;

    public IfNode(ExprNode condition, Node trueBranch, Node falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }
    @Override
    public void evaluate() {
        System.out.println("Condition is need to evaluate");
    }
}
