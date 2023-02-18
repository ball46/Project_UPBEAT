package Grammar.AST.Expr;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class OpponentNode extends Node.ExprNode {
    @Override
    public long eval(Game game){
        return game.opponent();
    }
    @Override
    public String toString() {
       return "Opponent";
    }
}
