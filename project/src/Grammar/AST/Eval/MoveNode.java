package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class MoveNode extends Node.EvalNode {
    private final String direction;

    public MoveNode(String direction) {
        this.direction = direction;
    }
    @Override
    public EvalNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
}
