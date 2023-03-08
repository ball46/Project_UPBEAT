package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;
import Type.Direction;

public class AttackNode extends Node.StateNode {
    private final Direction direction;
    private final Node.ExprNode expr;
    public AttackNode(Direction direction, ExprNode exprNode) {
        this.direction = direction;
        this.expr = exprNode;
    }
    @Override
    public boolean evaluate(Game game) {
        return game.attack(direction, expr.eval(game));
    }
}
