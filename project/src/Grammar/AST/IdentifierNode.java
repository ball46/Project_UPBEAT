package Grammar.AST;

import java.util.Map;

public class IdentifierNode extends ExprNode {
    private final String idf;
    public IdentifierNode(String idf) {
        this.idf = idf;
    }
    @Override
    public long eval(Map<String, Long> mem) {
        if(mem.containsKey(idf)){
            return mem.get(idf);
        }else{
            throw new RuntimeException("Identifier" + idf + " not found");
        }
    }
}