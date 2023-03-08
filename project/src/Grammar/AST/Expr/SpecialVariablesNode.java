package Grammar.AST.Expr;

import Game_state.Game.Game;
import Grammar.AST.ASTError;
import Grammar.AST.Node;

public class SpecialVariablesNode extends Node.ExprNode {
    private final String name;

    public SpecialVariablesNode(String name) {
        this.name = name;
    }

    @Override
    public long eval(Game game) {
        return switch (name) {
            case "rows" -> game.getRow();
            case "cols" -> game.getCol();
            case "currow" -> game.getCityCrewRow();
            case "curcol" -> game.getCityCrewCol();
            case "budget" -> game.getBudget();
            case "deposit" -> game.getDeposit();
            case "int" -> game.getInterest();
            case "maxdeposit" -> game.getMaxDeposit();
            case "random" -> game.getRandom();
            default -> throw new ASTError.UnknownSymbol(name);
        };
    }
}
