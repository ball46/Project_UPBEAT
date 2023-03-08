package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;
import Type.Direction;

public class MoveNode extends Node.StateNode {
    private final Direction direction;

    public MoveNode(Direction direction) {
        this.direction = direction;
    }
    @Override
    public boolean evaluate(Game game) {
        return game.move(direction);
    }
}
