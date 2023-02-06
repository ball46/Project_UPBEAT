package Grammar.AST;

public class AttackNode implements Nodes {
    private final String direction;
    private final long moneyattack;
    public AttackNode(String direction, long moneyattack) {
        this.direction = direction;
        this.moneyattack = moneyattack;
    }
    @Override
    public void evaluate() {
        System.out.println("Attackpower:" + moneyattack + " to " + direction);
    }
}
