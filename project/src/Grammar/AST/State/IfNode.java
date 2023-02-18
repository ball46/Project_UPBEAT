package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class IfNode extends Node.StateNode {
    private final ExprNode condition;
    private final StateNode trueBranch;
    private final StateNode falseBranch;

    public IfNode(ExprNode condition, StateNode trueBranch, StateNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }
    @Override
    public StateNode evaluate(Game game) {
        trueBranch.nextState = nextState;
        falseBranch.nextState = nextState;
        if(condition.eval(game) != 0){
            return trueBranch;
        }else {
            return falseBranch;
        }
    }
}
