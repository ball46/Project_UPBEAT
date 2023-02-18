package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

import java.util.Map;

public class AssignmentNode extends Node.EvalNode {
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
    public Node.EvalNode evaluate(Game game) {
        Map<String, Long> mem = game.getPlayer().getIdentifiers();
        mem.put(identifier, expression.eval(mem));
        return next;//TODO after implementing game state completely
    }
}
