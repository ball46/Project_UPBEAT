package Grammar.AST;

public class DoneNode implements Node {
    @Override
    public void evaluate() {
        System.out.println("turn ended");
    }
}
