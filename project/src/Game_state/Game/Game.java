package Game_state.Game;

import Game_state.Region.Region;
import Type.Direction;

import java.util.List;
import java.util.Map;

public interface Game {
    Map<String, Long> getIdentifiers();
    void attack(Direction direction, long money);
    boolean collect(long money);
    void invest(long money);
    void move(Direction direction);
    void relocate();
    long nearby(Direction direction);
    long opponent();
    List<Region> getTerritory();
    Region getRegion(int location);
    long getBudget();
    void endTurn();
}
