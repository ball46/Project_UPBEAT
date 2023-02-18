package Grammar.AST.Eval;
import Game_state.Game.Game;
import Grammar.AST.Node;

import java.util.List;

public class BlockNode extends Node.EvalNode {
    private List<Node> statements;
    public BlockNode(List<Node> statements) {
        this.statements = statements;
    }
    @Override
    public EvalNode evaluate(Game game) {
        return null;//TODO after implementing game state completely
    }
}
