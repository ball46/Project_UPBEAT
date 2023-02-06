package Grammar.AST;
import java.util.List;

public class BlockNode implements Nodes{
    List<Nodes> statements;
    @Override
    public void evaluate() {
        System.out.println("BlockNode is evaluated");
    }
}
