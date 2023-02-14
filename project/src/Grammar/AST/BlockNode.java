package Grammar.AST;
import java.util.List;

public class BlockNode implements Node {
    private List<Node> statements;
    public BlockNode(List<Node> statements) {
        this.statements = statements;
    }
    @Override
    public void evaluate() {
        System.out.println("BlockNode is evaluated");
    }
}
