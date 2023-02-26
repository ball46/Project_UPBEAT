package Game_state.Player;

import Type.Direction;

import java.util.Map;

public interface Player {
    boolean life();
    String getName();
    long getBudget();
    void updateBudget(double money);
    int getCityCrewLocation();
    int getCityCenterLocation();
    Map<String, Long> getIdentifiers();
}
