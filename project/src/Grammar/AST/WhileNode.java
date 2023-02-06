package Grammar.AST;

public class WhileNode implements Nodes{
    private final Nodes condition;
    private final Nodes body;
    public WhileNode(Nodes condition, Nodes body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public void evaluate() {
        System.out.println("WhileNode is evaluated");
    }
}
