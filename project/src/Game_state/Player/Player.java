package Game_state.Player;

import Game_state.Region.Region;

import java.util.Map;

public interface Player {
    boolean life();
    String getName();
    long getBudget();
    void updateBudget(double money);
    int getCityCrewLocation();
    int getCityCenterLocation();
    Region getCityCenter();
    Map<String, Long> getIdentifiers();
}
