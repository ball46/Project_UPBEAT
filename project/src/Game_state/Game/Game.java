package Game_state.Game;

import Game_state.Player.Player;
import Game_state.Region.Region;
import Type.Direction;

import java.util.List;
import java.util.Map;

public interface Game {
    Map<String, Long> getIdentifiers();
    boolean attack(Direction direction, long money);
    boolean collect(long money);
    boolean invest(long money);
    boolean move(Direction direction);
    boolean relocate();
    long nearby(Direction direction);
    long opponent();
    List<Region> getTerritory();
    Region getRegion(int location);
    Region getCityCrew();
    long getRow();
    long getCol();
    long getCityCrewRow();
    long getCityCrewCol();
    long getBudget();
    long getDeposit();
    long getInterest();
    long getMaxDeposit();
    long getRandom();
    Player getCurrentPlayer();
    Player getPlayer1();
    Player getPlayer2();
    void sendPlan(String plan);
}
