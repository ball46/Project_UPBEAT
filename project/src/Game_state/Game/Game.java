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
    void beginTurn();//this method is implemented for use to test the game is real game is use in the sendPlan
    void endTurn();//this method is same as beginTurn
    void sendPlan(String pathFile) throws IOException;
}
