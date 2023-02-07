package Grammar.AST;
import java.util.List;

public class BlockNode implements Node {
    private List<Node> statements;
    @Override
    public void evaluate() {
        System.out.println("BlockNode is evaluated");
    }
}
