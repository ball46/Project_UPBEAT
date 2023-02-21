package Grammar.Parser;

public class ParserError extends RuntimeException{
    public ParserError(String message) {
        super(message);
    }
    public static class CommandIsNoInput extends ParserError{
        public CommandIsNoInput() {
            super("No input please write the command");
        }
    }
    public static class CommandHasLeftoverToken extends ParserError{
        public CommandHasLeftoverToken(String message) {
            super("Token is not null : " + message);
        }
    }
    public static class CommandNotFound extends ParserError{
        public CommandNotFound(String message) {
            super("Expected " + message);
        }
    }
    public static class CommandIsUnknown extends ParserError{
        public CommandIsUnknown(String message) {
            super("Unknown Command: " + message);
        }
    }
    public static class DirectionCommandIsUnknown extends ParserError{
        public DirectionCommandIsUnknown(String message) {
            super("Unknown Direction: " + message);
        }
    }
    public static class InfoCommandIsUnknown extends ParserError{
        public InfoCommandIsUnknown(String message) {
            super("Unknown info expression: " + message);
        }
    }

    public static class CommandHasSpecialVariable extends ParserError {
        public CommandHasSpecialVariable(String identifier) {
            super(identifier + " is a special variable");
        }
    }
}
