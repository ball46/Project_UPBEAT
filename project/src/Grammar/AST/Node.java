package Grammar.AST;

import Game_state.Game.Game;

public class Node {
    public static class StateNode extends Node{
        public StateNode nextState;
        public boolean evaluate(Game game) {
            return false;
        }
    }

    public static class ExprNode extends Node {
        public long eval(Game game){
            return 0;
        }
        public String toString(){
            return "";
        }
    }
}
