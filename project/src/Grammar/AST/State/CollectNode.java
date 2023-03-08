package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class CollectNode extends Node.StateNode {
    private final Node.ExprNode expr;

    public CollectNode(Node.ExprNode expr) {
        this.expr = expr;
    }
    @Override
    public boolean evaluate(Game game) {
        return game.collect(expr.eval(game));
    }
}
