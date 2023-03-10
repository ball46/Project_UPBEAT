package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class InvestNode extends Node.StateNode {
    private final Node.ExprNode expr;
    public InvestNode(ExprNode expr) {
        this.expr = expr;
    }
    @Override
    public boolean evaluate(Game game) {
        return game.invest(expr.eval(game));
    }
}
