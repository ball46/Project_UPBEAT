package Game_state.Player;

import java.util.HashMap;
import java.util.Map;

public class Player_im implements Player{
    private final String name;
    private double budget;
    private boolean life;
    private final int[] CityCenter, CityCrew;//has 2 member [0] is rows, [1] is cols
    private final Map<String, Long> identifier;
    public Player_im(String name,double budget, double centerDep) {
        this.name = name;
        this.budget = budget;
        this.life = true;
        this.CityCenter = new int[2];//TODO change it in future
        this.CityCrew = this.CityCenter;
        this.identifier = new HashMap<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getBudget() {
        return this.budget;
    }

    @Override
    public void updateBudget(double money) {
        this.budget += money;
    }

    @Override
    public void moveCityCrew(String direction) {
        switch (direction) {
            case "up" -> this.CityCrew[0]--;
            case "down" -> this.CityCrew[0]++;
            case "upleft" -> {
                this.CityCrew[0]--;
                this.CityCrew[1]--;
            }
            case "upright" -> {
                this.CityCrew[0]--;
                this.CityCrew[1]++;
            }
            case "downleft" -> this.CityCrew[1]--;
            case "downright" -> this.CityCrew[1]++;
        }
    }

    @Override
    public int[] getCityCrewLocation() {
        return this.CityCrew;
    }

    @Override
    public int[] getCityCenterLocation() {
        return this.CityCenter;
    }

    @Override
    public boolean life() {
        return this.life;
    }

    @Override
    public long attack(String direction) {
        return 0;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifier;
    }
}