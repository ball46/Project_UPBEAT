package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class CollectNode extends Node.EvalNode {
    private final Node.ExprNode expr;

    public CollectNode(Node.ExprNode expr) {
        this.expr = expr;
    }
    @Override
    public EvalNode evaluate(Game game) {
        return new EvalNode();
    }
}
