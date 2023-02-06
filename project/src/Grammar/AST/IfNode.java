package Grammar.AST;

public class IfNode implements Nodes{
    private final ExprNodes condition;
    private final Nodes trueBranch;
    private final Nodes falseBranch;

    public IfNode(ExprNodes condition, Nodes trueBranch, Nodes falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }
    @Override
    public void evaluate() {
        System.out.println("Condition is need to evaluate");
    }
}
