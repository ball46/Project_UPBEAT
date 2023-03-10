package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class RelocateNode extends Node.StateNode {
    @Override
    public boolean evaluate(Game game) {
        game.relocate();
        return true;
    }
}
