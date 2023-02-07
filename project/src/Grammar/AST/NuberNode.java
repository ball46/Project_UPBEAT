package Grammar.AST;

import java.util.Map;

public class NuberNode extends ExprNode {
    private final long value;

    public NuberNode(long value) {
        this.value = value;
    }

    @Override
    public long eval(Map<String, Long> mem) {
        return value;
    }
}
