package Grammar.AST.Expr;

import Grammar.AST.Node;

import java.util.Map;

public class NumberNode extends Node.ExprNode {
    private final long value;

    public NumberNode(long value) {
        this.value = value;
    }

    @Override
    public long eval(Map<String, Long> memory) {
        return value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
