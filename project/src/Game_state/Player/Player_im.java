package Game_state.Player;

import Game_state.Region.Region;

import java.util.HashMap;
import java.util.Map;

public class Player_im implements Player{
    private final String name;
    private long budget;
    private Region CityCenter;//location is row * col
    private final Map<String, Long> identifier;
    public Player_im(String name, long budget, Region CityCenter) {
        this.name = name;
        this.budget = budget;
        this.CityCenter = CityCenter;
        this.identifier = new HashMap<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getBudget() {
        return this.budget;
    }

    @Override
    public void updateBudget(long money) {
        this.budget = Math.max(0, this.budget + money);
    }

    @Override
    public int getCityCenterLocation() {
        return this.CityCenter.getLocation();
    }

    @Override
    public Region getCityCenter() {
        return this.CityCenter;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return this.identifier;
    }

    @Override
    public void updateCityCenter(Region to) {
        CityCenter.updateOwner(null);
        CityCenter = to;
        CityCenter.updateOwner(this);
    }
}
