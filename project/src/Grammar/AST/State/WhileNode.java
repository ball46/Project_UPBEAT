package Grammar.AST.State;

import Game_state.Game.Game;

public class WhileNode extends IfNode {
    private long count = 0;
    public WhileNode(ExprNode condition, StateNode body) {
        super(condition, body, null);
        if(trueBranch == null) trueBranch = this;
    }
    @Override
    public boolean evaluate(Game game) {
        if(condition.eval(game) > 0 && count < 10000){
            count++;
            if(!trueBranch.evaluate(game)){
                return false;
            }
            return evaluate(game);
        }
        return true;
    }
}
