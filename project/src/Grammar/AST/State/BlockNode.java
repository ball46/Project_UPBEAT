package Grammar.AST.State;
import Game_state.Game.Game;
import Grammar.AST.Node;

import java.util.List;

public class BlockNode extends Node.StateNode {
    private final List<Node> statements;
    public BlockNode(List<Node> statements) {
        this.statements = statements;
    }
    @Override
    public StateNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
}
