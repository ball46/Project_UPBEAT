package Game_state.Game;

import Game_state.Player.Player;
import Game_state.Region.Region;

import java.util.List;

public interface Game {
    Player getPlayer();
    Region getRegion();
    List<Region> getTerritory();
}
