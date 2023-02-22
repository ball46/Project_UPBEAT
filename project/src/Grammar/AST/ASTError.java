package Grammar.AST;

public class ASTError extends RuntimeException{
    public ASTError(String message) {
        super(message);
    }
    public static class UnknownSymbol extends ASTError{
        public UnknownSymbol(String message) {
            super("Unknown operator: " + message);
        }
    }
    public static class UnknownIdentifier extends ASTError{
        public UnknownIdentifier(String message) {
            super("Identifier" + message + " not found");
        }
    }

}
