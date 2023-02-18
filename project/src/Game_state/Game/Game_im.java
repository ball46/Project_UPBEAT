package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;

import java.util.List;

public class Game_im implements Game{
    private final Player player;
    private final Region region;
    private final List<Region> territory;
    public Game_im(Player player, Region region, List<Region> territory) {
        this.player = new Player_im("ball");
        this.region = new Region_im(0,0);
        this.territory = territory;
    }
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public List<Region> getTerritory() {
        return territory;
    }
}
