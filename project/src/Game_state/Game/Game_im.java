package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import Type.Direction;

import java.util.List;
import java.util.Map;

public class Game_im implements Game{
    private final Player player1, player2;
    private final List<Region> territory;
    private final long actionCost = 1;
    private Player current_player;
    private Region cityCrew;
    public Game_im(String nameP1, String nameP2) {
        this.territory = ReadData.createMap();
        this.player1 = ReadData.createPlayer(nameP1);
        this.player2 = ReadData.createPlayer(nameP2);
        this.current_player = this.player1;
    }

    private boolean checkBudget() {
        return current_player.getBudget() >= actionCost;
    }

    private Region CityCenter() {
        return territory.get(current_player.getCityCenterLocation());
    }

    private Region CityCrew() {
        return territory.get(current_player.getCityCrewLocation());
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return current_player.getIdentifiers();
    }

    @Override
    public void attack(Direction direction, long money) {
        //TODO implement
    }

    @Override
    public boolean collect(long money) {
        if(money < 0 || !checkBudget())
            return false;
        current_player.updateBudget(-actionCost);
        Region targetRegion = CurrentCityCrew();
        if(targetRegion.getOwner() != current_player)
            return false;
        if(money > targetRegion.getDeposit()){
            return false;
        }
        current_player.updateBudget(money);
        targetRegion.updateDeposit(-money);
        if(targetRegion.getDeposit() == 0)
            targetRegion.updateOwner(null);
        return true;
    }
    private Region CurrentCityCenter() {
        return CityCenter();
    }

    private Region CurrentCityCrew() {
        return CityCrew();
    }

    @Override
    public void invest(long money) {
        current_player.updateBudget(-actionCost);

    }

    @Override
    public boolean move(Direction direction) {
        if(checkBudget()) {
            int location = current_player.getCityCrewLocation();
            switch (direction) {
                case Up -> location -= ReadData.getCols();
                case Down -> location += ReadData.getCols();
                case UpLeft -> {
                    if (location % 2 == 0) location -= ReadData.getCols() + 1;
                    else location--;
                }
                case UpRight -> {
                    if (location % 2 == 0) location += 1 - ReadData.getCols();
                    else location++;
                }
                case DownLeft -> {
                    if (location % 2 == 0) location--;
                    else location += ReadData.getCols() - 1;
                }
                case DownRight -> {
                    if (location % 2 == 0) location++;
                    else location += ReadData.getCols() + 1;
                }
            }
            if(territory.get(location).getOwner() != null) return false;
            cityCrew = territory.get(location);
            return true;
        }
        return false;
    }

    public void beginTurn() {
        this.cityCrew = current_player.getCityCenter();
    }

    @Override
    public void relocate() {
    }

    @Override
    public long nearby(Direction direction) {
        return 0;
    }

    @Override
    public long opponent() {
        return 0;
    }

    @Override
    public List<Region> getTerritory() {
        return this.territory;
    }

    @Override
    public Region getRegion(int location) {
        return this.territory.get(location);
    }

    @Override
    public long getBudget() {
        return current_player.getBudget();
    }

    @Override
    public void endTurn() {
        if(current_player == this.player1){
            current_player = this.player2;
        }else{
            current_player = this.player1;
        }
    }
    //action command is pay 1 coin
    //information command is pay 0 coin
}
