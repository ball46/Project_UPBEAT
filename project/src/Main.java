import Game_state.Game.Game_im;
import Grammar.AST.Node;
import Grammar.Parser.Parser;
import Grammar.Parser.Parser_im;
import Grammar.Tokenizer.Tokenizer_im;

public class Main {
    public static void main(String[] args) {
        Parser a = new Parser_im(new Tokenizer_im("done"));
        Node.StateNode e = a.parse();
        System.out.println(e.evaluate(new Game_im()));
    }
}