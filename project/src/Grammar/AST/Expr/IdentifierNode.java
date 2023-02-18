package Grammar.AST.Expr;

import Grammar.AST.Node;

import java.util.Map;

public class IdentifierNode extends Node.ExprNode {
    private final String idf;
    public IdentifierNode(String idf) {
        this.idf = idf;
    }
    @Override
    public long eval(Map<String, Long> memory) {
        if(memory.containsKey(idf)){
            return memory.get(idf);
        }else{
            throw new RuntimeException("Identifier" + idf + " not found");
        }
    }

    @Override
    public String toString(){
        return idf;
    }
}
