package Grammar.AST.Expr;

import Grammar.AST.Node;

public class NearbyNode extends Node.ExprNode {
    private final String direction;

    public NearbyNode(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "NearbyNode " + direction;
    }
}
