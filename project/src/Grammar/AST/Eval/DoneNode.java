package Grammar.AST.Eval;

import Game_state.Game.Game;
import Grammar.AST.Node;

public class DoneNode extends Node.EvalNode {
    @Override
    public EvalNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
//    public void evaluate(Player player) {
//        System.out.println("turn ended");
//    }
}
