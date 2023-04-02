package Game_state.Player;

import Game_state.Region.Region;

import java.util.Map;

public interface Player {
    String getName();
    long getBudget();
    void updateBudget(long money);
    int getCityCenterLocation();
    Region getCityCenter();
    Map<String, Long> getIdentifiers();
    void updateCityCenter(Region to);
}
