import Game_state.Game.Game_im;
import Grammar.AST.Node;
import Grammar.Parser.Parser;
import Grammar.Parser.Parser_im;
import Grammar.Tokenizer.Tokenizer_im;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
//        Parser a = new Parser_im(new Tokenizer_im("done"));
//        Node.StateNode e = a.parse();
//        System.out.println(e.evaluate(new Game_im()));
        Path fileName = Path.of("D:\\UPBEAT\\project\\src\\Construction\\Testfile.txt");
        String str = Files.readString(fileName);
        System.out.println(str);
//        Parser test = new Parser_im(new Tokenizer_im(str));
    }
}