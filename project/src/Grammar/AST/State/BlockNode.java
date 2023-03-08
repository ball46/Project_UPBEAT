package Grammar.AST.State;

import Game_state.Game.Game;
import Grammar.AST.Node;

import java.util.List;

public class BlockNode extends Node.StateNode {
    private final List<Node.StateNode> nodes;

    public BlockNode(List<StateNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean evaluate(Game game) {
        for(Node.StateNode node : nodes){
            if(!node.evaluate(game)) return false;
        }
        return true;
    }
}
