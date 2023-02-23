package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class WhileNode extends IfNode {
    private long count = 0;
    public WhileNode(ExprNode condition, StateNode body) {
        super(condition, body, null);
        if(trueBranch == null) trueBranch = this;
    }
    @Override
    public Node.StateNode evaluate(Game game) {
        if(condition.eval(game) > 0){
            if(count >= 3000) return nextState;
            Node.StateNode last = getLastState(trueBranch);
            if(last!= this) last.nextState = this;
            count++;
            return trueBranch;
        }
        return nextState;
    }

    private Node.StateNode getLastState(StateNode state) {
        while(state != this && state != null){
            if(state.nextState == this || state.nextState == null) return state;
            state = state.nextState;
        }
        return this;
    }
}
