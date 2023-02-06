package Grammar.AST;

public class RegionNode implements Nodes{
    private final long amount;
    private final String actionRe;
    public RegionNode(long amount, String actionRe) {
        this.amount = amount;
        this.actionRe = actionRe;
    }
    @Override
    public void evaluate() {
        System.out.println("This region has " + amount + " units of action is " + actionRe);
    }
}
