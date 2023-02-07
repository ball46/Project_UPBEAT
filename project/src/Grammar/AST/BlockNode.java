package Grammar.AST;
import java.util.List;

public class BlockNode implements Nodes{
    private List<Nodes> statements;
    @Override
    public void evaluate() {
        System.out.println("BlockNode is evaluated");
    }
}
