package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

import java.util.Map;

public class AssignmentNode extends Node.StateNode {
    private final String identifier;
    private final Node.ExprNode expression;

    public AssignmentNode(String identifier, ExprNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }
    public String getIdentifier() {
        return identifier;
    }
    public Node.ExprNode getExpression() {
        return expression;
    }
    @Override
    public StateNode evaluate(Game game) {
        Map<String, Long> mem = game.getIdentifiers();
        mem.put(identifier, expression.eval(game));
        return nextState;
    }
}
