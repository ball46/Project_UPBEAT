package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class RelocateNode extends Node.StateNode {
    @Override
    public StateNode evaluate(Game game) {
        game.relocate();
        return nextState;//TODO is can change if relocate is done turn is end
    }
}
