package Grammar.AST;

public class WhileNode implements Node {
    private final Node condition;
    private final Node body;
    public WhileNode(Node condition, Node body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public void evaluate() {
        System.out.println("WhileNode is evaluated");
    }
}
