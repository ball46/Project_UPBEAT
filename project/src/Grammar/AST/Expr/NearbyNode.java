package Grammar.AST.Expr;

import Game_state.Game.Game;
import Grammar.AST.Node;
import Type.Direction;

public class NearbyNode extends Node.ExprNode {
    private final Direction direction;

    public NearbyNode(Direction direction) {
        this.direction = direction;
    }
    @Override
    public long eval(Game game){
        return game.nearby(direction);
    }

    @Override
    public String toString() {
        return "Nearby " + direction;
    }
}
