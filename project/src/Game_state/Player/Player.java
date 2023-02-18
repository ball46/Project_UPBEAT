package Game_state.Player;

import java.util.Map;

public interface Player {
    String getName();
    double getBudget();
    void updateBudget(double money);
    void moveCityCrew(String direction);
    void moveCityCenter(int[] direction);
    int[] getCityCrewLocation();
    int[] getCityCenterLocation();
    boolean life();
    long attack();
    Map<String,Long> getIdentifiers();
}
