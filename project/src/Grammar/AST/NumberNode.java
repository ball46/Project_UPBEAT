package Grammar.AST;

import java.util.Map;

public class NumberNode extends ExprNode {
    private final long value;

    public NumberNode(long value) {
        this.value = value;
    }

    @Override
    public long eval(Map<String, Long> mem) {
        return value;
    }
}
