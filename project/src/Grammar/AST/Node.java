package Grammar.AST;

import Game_state.Game.Game;

import java.util.Map;

public class Node {
    public static class EvalNode extends Node{
        public EvalNode next;
        public EvalNode evaluate(Game game) {
            return new EvalNode();
        }
    }

    public static class ExprNode extends Node {
        public long eval(Map<String, Long> memory){
            return 0;
        }
        public String toString(){
            return "";
        }
    }
}
