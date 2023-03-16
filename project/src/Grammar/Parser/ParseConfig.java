package Grammar.Parser;

import Grammar.AST.Node;
import Grammar.Tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class ParseConfig extends Parser_im{
    public ParseConfig(Tokenizer tkz) {
        super(tkz);
    }

    @Override
    public List<Node.StateNode> parse() {
        List<Node.StateNode> parameters = new ArrayList<>();
        parseStatements(parameters);
        return parameters;
    }

}
