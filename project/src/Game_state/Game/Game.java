package Game_state.Game;

import Game_state.Region.Region;
import Type.Direction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Game {
    Map<String, Long> getIdentifiers();
    void attack(Direction direction, long money);
    boolean collect(long money);
    void invest(long money);
    boolean move(Direction direction);
    void relocate();
    long nearby(Direction direction);
    long opponent();
    List<Region> getTerritory();
    Region getRegion(int location);
    Region getCityCrew();
    long getBudget();
    void sendPlan(String pathFile) throws IOException;
}
