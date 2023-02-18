package Grammar.AST.Expr;

import Grammar.AST.Node;
import Type.Direction;

public class NearbyNode extends Node.ExprNode {
    private final Direction direction;

    public NearbyNode(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Nearby " + direction;
    }
}
