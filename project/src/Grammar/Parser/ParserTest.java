package Grammar.Parser;

import Grammar.AST.Node;
import Grammar.AST.State.AssignmentNode;
import Grammar.Tokenizer.Tokenizer_im;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    public Parser parser;
    public List<Node.StateNode> stateNode;
    @Test
    public void testStatementIsEmpty() {
        assertThrows(RuntimeException.class, () -> new Parser_im(new Tokenizer_im("")));
    }
    @Test
    public void testStatements(){
        parser = new Parser_im(new Tokenizer_im("x = 1 y = 2 z = 3 a = 4 b = 5"));
        stateNode = parser.parse();
        assertInstanceOf(AssignmentNode.class, stateNode.get(0));
        assertInstanceOf(AssignmentNode.class, stateNode.get(1));
        assertInstanceOf(AssignmentNode.class, stateNode.get(2));
        assertInstanceOf(AssignmentNode.class, stateNode.get(3));
        assertInstanceOf(AssignmentNode.class, stateNode.get(4));
    }
    @Test
    public void testSpecialVariable() {
        parser = new Parser_im((new Tokenizer_im("nearby = 10")));
        assertThrows(ParserError.CommandHasNotUseVariable.class,() -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("opponent = 10")));
        assertThrows(ParserError.CommandHasNotUseVariable.class,() -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("int = 10")));
        assertThrows(ParserError.CommandHasNotUseVariable.class,() -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("rows = 10")));
        assertThrows(ParserError.CommandHasNotUseVariable.class,() -> parser.parse());
    }
    @Test
    public void testUnknownWord() {
        parser = new Parser_im((new Tokenizer_im("ball")));
        assertThrows(ParserError.CommandNotFound.class, () -> parser.parse());
        parser = new Parser_im((new Tokenizer_im("boss")));
        assertThrows(ParserError.CommandNotFound.class, () -> parser.parse());
    }
    @Test
    public void testEval() {
        parser = new Parser_im((new Tokenizer_im("1+2")));
        assertThrows(ParserError.CommandNotFound.class, () -> parser.parse());
    }
    @Test
    public void testReadFile(){
        String str = """
                t = t + 1  # keeping track of the turn number
                m = 0  # number of random moves
                while (deposit) { # still our region
                  if (deposit - 100)
                  then collect (deposit / 4)  # collect 1/4 of available deposit
                  else if (budget - 25) then invest 25
                  else {}
                  if (budget - 100) then {} else done  # too poor to do anything else
                  opponentLoc = opponent
                  if (opponentLoc / 10 - 1)
                  then  # opponent afar
                    if (opponentLoc % 10 - 5) then move downleft
                    else if (opponentLoc % 10 - 4) then move down
                    else if (opponentLoc % 10 - 3) then move downright
                    else if (opponentLoc % 10 - 2) then move up
                    else if (opponentLoc % 10 - 1) then move upright
                    else move up
                  else if (opponentLoc)
                  then  # opponent adjacent to city crew
                    if (opponentLoc % 10 - 5) then {
                      cost = 10 ^ (nearby upleft % 100 + 1)
                      if (budget - cost) then shoot upleft cost else {}
                    }
                    else if (opponentLoc % 10 - 4) then {
                      cost = 10 ^ (nearby downleft % 100 + 1)
                      if (budget - cost) then shoot downleft cost else {}
                    }
                    else if (opponentLoc % 10 - 3) then {
                      cost = 10 ^ (nearby down % 100 + 1)
                      if (budget - cost) then shoot down cost else {}
                    }
                    else if (opponentLoc % 10 - 2) then {
                      cost = 10 ^ (nearby downright % 100 + 1)
                      if (budget - cost) then shoot downright cost else {}
                    }
                    else if (opponentLoc % 10 - 1) then {
                      cost = 10 ^ (nearby upright % 100 + 1)
                      if (budget - cost) then shoot upright cost else {}
                    }
                    else {
                      cost = 10 ^ (nearby up % 100 + 1)
                      if (budget - cost) then shoot up cost else {}
                    }
                  else {  # no visible opponent; move in a random direction
                    dir = random % 6
                    if (dir - 4) then move upleft
                    else if (dir - 3) then move downleft
                    else if (dir - 2) then move down
                    else if (dir - 1) then move downright
                    else if (dir) then move upright
                    else move up
                    m = m + 1
                  }
                }
                """;
        parser = new Parser_im((new Tokenizer_im(str)));
        assertDoesNotThrow(() -> parser.parse());
    }
}