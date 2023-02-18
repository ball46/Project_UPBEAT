package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class WhileNode extends Node.EvalNode {
    private final ExprNode condition;
    private final EvalNode body;
    public WhileNode(ExprNode condition, EvalNode body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public EvalNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
}
