package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class AttackNode extends Node.EvalNode {
    private final String direction;
    private final Node.ExprNode expr;
    public AttackNode(String direction, ExprNode exprNode) {
        this.direction = direction;
        this.expr = exprNode;
    }
    @Override
    public Node.EvalNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
}
