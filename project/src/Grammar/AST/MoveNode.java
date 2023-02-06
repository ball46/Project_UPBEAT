package Grammar.AST;

public class MoveNode implements Nodes{
    private final String direction;

    public MoveNode(String direction) {
        this.direction = direction;
    }
    @Override
    public void evaluate() {
        System.out.println("Move to " + direction);
    }
}
