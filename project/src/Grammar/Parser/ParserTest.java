package Grammar.Parser;

import Grammar.AST.Node;
import Grammar.AST.State.AssignmentNode;
import Grammar.Tokenizer.Tokenizer_im;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    public Parser parser;
    public Node.StateNode stateNode;
    @Test
    public void testStatementIsEmpty() {
        assertThrows(RuntimeException.class, () -> new Parser_im(new Tokenizer_im("")));
    }
    @Test
    public void testStatements(){
        parser = new Parser_im(new Tokenizer_im("x = 1 y = 2 z = 3 a = 4 b = 5"));
        stateNode = parser.parse();
        assertInstanceOf(AssignmentNode.class, stateNode);
        stateNode = stateNode.nextState;
        assertInstanceOf(AssignmentNode.class, stateNode);
        stateNode = stateNode.nextState;
        assertInstanceOf(AssignmentNode.class, stateNode);
        stateNode = stateNode.nextState;
        assertInstanceOf(AssignmentNode.class, stateNode);
        stateNode = stateNode.nextState;
        assertInstanceOf(AssignmentNode.class, stateNode);
        stateNode = stateNode.nextState;
        assertNull(stateNode);
    }

    @Test
    public void testSpecialVariable() {
        parser = new Parser_im((new Tokenizer_im("nearby = 10")));
        assertThrows(ParserError.CommandHasSpecialVariable.class,() -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("opponent = 10")));
        assertThrows(ParserError.CommandHasSpecialVariable.class,() -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("int = 10")));
        assertThrows(ParserError.CommandHasSpecialVariable.class,() -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("rows = 10")));
        assertThrows(ParserError.CommandHasSpecialVariable.class,() -> parser.parse());
    }
}