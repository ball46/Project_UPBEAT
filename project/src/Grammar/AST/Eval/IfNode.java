package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class IfNode extends Node.EvalNode {
    private final ExprNode condition;
    private final EvalNode trueBranch;
    private final EvalNode falseBranch;

    public IfNode(ExprNode condition, EvalNode trueBranch, EvalNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }
    @Override
    public EvalNode evaluate(Game game) {
        trueBranch.next = next;
        falseBranch.next = next;
        if(condition.eval(game.getPlayer().getIdentifiers()) != 0){
            return trueBranch;
        }else {
            return falseBranch;
        }
    }
}
