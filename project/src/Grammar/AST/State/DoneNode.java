package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class DoneNode extends Node.StateNode {
    @Override
    public boolean evaluate(Game game) {
        return true;
    }
}
