package Grammar.Parser;

import Grammar.AST.Node;

public interface Parser {
    Node.EvalNode parse();
}
