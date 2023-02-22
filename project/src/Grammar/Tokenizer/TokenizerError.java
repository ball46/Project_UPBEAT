package Grammar.Tokenizer;

public class TokenizerError extends RuntimeException {
    public TokenizerError(String message) {
        super(message);
    }
    public static class NextNull extends TokenizerError {
        public NextNull() {
            super("No more tokens");
        }
    }
    public static class UnknownWord extends TokenizerError {
        public UnknownWord(char c) {
            super("Invalid character : " + c);
        }
    }
}
