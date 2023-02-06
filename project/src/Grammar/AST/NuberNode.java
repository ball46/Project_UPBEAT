package Grammar.AST;

import java.util.Map;

public class NuberNode extends ExprNodes{
    private final int value;

    public NuberNode(int value) {
        this.value = value;
    }

    @Override
    public long eval(Map<String, Long> mem) {
        return value;
    }
}
