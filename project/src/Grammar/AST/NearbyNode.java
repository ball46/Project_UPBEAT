package Grammar.AST;

public class NearbyNode extends ExprNodes{
    private final String direction;

    public NearbyNode(String direction) {
        this.direction = direction;
    }

    @Override
    public void evaluate() {
        System.out.println("NearbyNode " + direction);
    }
}
