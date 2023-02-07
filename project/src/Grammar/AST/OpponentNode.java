package Grammar.AST;

public class OpponentNode extends ExprNode {
    @Override
    public void evaluate() {
        System.out.println("Opponent");
    }
}
