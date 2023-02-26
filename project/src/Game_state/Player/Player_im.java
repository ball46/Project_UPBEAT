package Game_state.Player;

import Game_state.Region.Region;

import java.util.HashMap;
import java.util.Map;

public class Player_im implements Player{
    private final String name;
    private long budget;
    private boolean life;
    private Region CityCenter, CityCrew;//location is row * col
    private final Map<String, Long> identifier;
    public Player_im(String name, long budget, Region CityCenter) {
        this.name = name;
        this.budget = budget;
        this.life = true;
        this.CityCenter = CityCenter;
        this.CityCrew = this.CityCenter;
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
    public void updateBudget(double money) {
        this.budget += money;
    }

    @Override
    public int getCityCrewLocation() {
        return this.CityCrew.getLocation();
    }

    @Override
    public int getCityCenterLocation() {
        return this.CityCenter.getLocation();
    }

    @Override
    public boolean life() {
        return this.life;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return this.identifier;
    }
}
