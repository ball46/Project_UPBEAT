package Grammar.AST;

public class DoneNode implements Nodes {
    @Override
    public void evaluate() {
        System.out.println("turn ended");
    }
}
