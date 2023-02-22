package Grammar.AST.Expr;

import Game_state.Game.Game;
import Grammar.AST.ASTError;
import Grammar.AST.Node;

public class IdentifierNode extends Node.ExprNode {
    private final String idf;
    public IdentifierNode(String idf) {
        this.idf = idf;
    }
    @Override
    public long eval(Game game) {
        if(game.getIdentifiers().containsKey(idf)){
            return game.getIdentifiers().get(idf);
        }else{
            throw new ASTError.UnknownIdentifier(idf);
        }
    }

    @Override
    public String toString(){
        return idf;
    }
}
