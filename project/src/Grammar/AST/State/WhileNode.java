package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class WhileNode extends Node.StateNode {
    private final ExprNode condition;
    private final StateNode body;
    public WhileNode(ExprNode condition, StateNode body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public StateNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
}
