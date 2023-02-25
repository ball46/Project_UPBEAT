package Game_state.Game;

public class GameError extends RuntimeException {
    public GameError(String message) {
        super(message);
    }
    public static class ConfigurationError extends GameError {
        public ConfigurationError(String message) {
            super(message);
        }
    }
}
