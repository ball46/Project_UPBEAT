package Grammar.AST.Expr;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class NumberNode extends Node.ExprNode {
    private final long value;

    public NumberNode(long value) {
        this.value = value;
    }

    @Override
    public long eval(Game game) {
        return value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
