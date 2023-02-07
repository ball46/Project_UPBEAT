package Grammar.Tokenizer;

public class TokenizerError extends RuntimeException {
    public TokenizerError(String message) {
        super(message);
    }
    public static class Nextnull extends TokenizerError {
        public Nextnull() {
            super("no more tokens");
        }
    }
    public static class Unknownword extends TokenizerError {
        public Unknownword(char c) {
            super("invalid character" + c);
        }
    }
}
