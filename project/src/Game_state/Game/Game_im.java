package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import Type.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game_im implements Game{
    private final Player player1, player2;
    private Player current_player;
    private final List<Region> territory;
    public Game_im(String nameP1, String nameP2) {
        this.player1 = ReadData.createPlayer(nameP1);
        this.player2 = ReadData.createPlayer(nameP2);
        this.territory = ReadData.createMap();
        this.current_player = this.player1;
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
    public void collect(long money) {
        if(money <= 0)
            throw new GameError.MoneyIsLessThanZero(money);
        if(current_player.getBudget() < 1)
            return;
        Region region = CurrentCityCrew();
        if(!region.getOwner().getName().equals(current_player.getName()))
            return;
        if(money > region.getDeposit())
            return;
        region.updateDeposit(-money);
        current_player.updateBudget(money);
        if(region.getDeposit() == 0)
            region.updateOwner(null);
    }
    private Region CurrentCityCenter() {
        return this.territory.get(current_player.getCityCenterLocation());
    }

    private Region CurrentCityCrew() {
        return this.territory.get(current_player.getCityCrewLocation());
    }

    @Override
    public void invest(long money) {

    }

    @Override
    public void move(Direction direction) {

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
}
