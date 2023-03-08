package Grammar.Parser;

import Grammar.AST.Node;

import java.util.List;

public interface Parser {
    List<Node.StateNode> parse();
}
