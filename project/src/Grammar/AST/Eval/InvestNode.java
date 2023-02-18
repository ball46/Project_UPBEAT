package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class InvestNode extends Node.EvalNode{
    private final Node.ExprNode expr;
    public InvestNode(ExprNode expr) {
        this.expr = expr;
    }
    @Override
    public EvalNode evaluate(Game game) {
        return new EvalNode();
    }
}
