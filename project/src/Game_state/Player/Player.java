package Game_state.Player;

import Type.Direction;

import java.util.Map;

public interface Player {
    boolean life();
    String getName();
    long getBudget();
    void updateBudget(double money);
    void moveCityCrew(Direction direction);
    int getCityCrewLocation();
    int getCityCenterLocation();
    long attack(String direction);
    Map<String, Long> getIdentifiers();
}
