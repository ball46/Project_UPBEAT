package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class IfNode extends Node.StateNode {
    private final Node.ExprNode condition;
    private final Node.StateNode trueBranch;
    private final Node.StateNode falseBranch;

    public IfNode(Node.ExprNode condition, Node.StateNode trueBranch, Node.StateNode falseBranch) {
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
