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
    public Game_im(String nameP1, String nameP2, List<Region> territory) {
        this.territory = territory;
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
        if(checkBudget()) {
            current_player.updateBudget(-actionCost);
            if(current_player.getBudget() < money || money < 0) return;
            Region targetRegion = territory.get(mockMove(direction));
            if(targetRegion != null){
                if(money < targetRegion.getDeposit()) {
                    targetRegion.updateDeposit(-money);
                }else{
                    targetRegion.updateDeposit(-targetRegion.getDeposit());
                    targetRegion.updateOwner(null);
                }
                current_player.updateBudget(-money);
            }
        }
    }

    @Override
    public boolean collect(long money) {
        if(money < 0 || !checkBudget())
            return false;
        current_player.updateBudget(-actionCost);
        Region targetRegion = CityCrew();
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

    @Override
    public void invest(long money) {
        if(checkBudget()) {
            long invested = cityCrew.getDeposit();
            long max_dep = ReadData.getMaxDeposit();
            current_player.updateBudget(-actionCost);
            if(current_player.getBudget() < money) return;
            if(cityCrew.getOwner() != null && cityCrew.getOwner() != current_player) return;
            if(invested == max_dep) return;
            if(invested + money > max_dep){
                long invest = max_dep - invested;
                cityCrew.updateDeposit(invest);
                current_player.updateBudget(-invest);
            }else {
                cityCrew.updateDeposit(money);
                current_player.updateBudget(-money);
            }
            cityCrew.updateOwner(current_player);
        }
    }

    public int mockMove(Direction direction){
        int location = current_player.getCityCrewLocation();
        int column = (int) ReadData.getCols();
        switch (direction) {
            case Up -> location -= column;
            case Down -> location += column;
            case UpLeft -> {
                if (location % 2 == 0) location -= column + 1;
                else location--;
            }
            case UpRight -> {
                if (location % 2 == 0) location += 1 - column;
                else location++;
            }
            case DownLeft -> {
                if (location % 2 == 0) location--;
                else location += column - 1;
            }
            case DownRight -> {
                if (location % 2 == 0) location++;
                else location += column + 1;
            }
        }
        return location;
    }

    @Override
    public boolean move(Direction direction) {
        if(checkBudget()) {
            current_player.updateBudget(-actionCost);
            int location = mockMove(direction);
            if(territory.get(location).getOwner() != null && territory.get(location).getOwner() != current_player) return false;
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
        if(checkBudget()){
            current_player.updateBudget(-actionCost);
            if(cityCrew.getOwner() == current_player) {
                int cityCenterRow = current_player.getCityCenter().getRow();
                int cityCenterCol = current_player.getCityCenter().getCol();
                int cityCrewRow = cityCrew.getRow();
                int cityCrewCol = cityCrew.getCol();
                int distance = (int) Math.floor(Math.sqrt(Math.pow(cityCenterRow - cityCrewRow, 2) + Math.pow(cityCenterCol - cityCrewCol, 2)));
                if (distance < 0) distance = 1;
                int cost = (5 * distance) + 10;
                if (current_player.getBudget() >= cost) {
                    current_player.updateBudget(-cost);
                    CityCenter().updateOwner(null);
                    cityCrew.updateOwner(current_player);
                    current_player.updateCityCenter(cityCrew);
                }
            }
        }
    }

    @Override
    public long nearby(Direction direction) {
        //TODO implement
        int currentRowRegion = cityCrew.getRow();
        int currentColRegion = cityCrew.getCol();
        int distance = 0;
        int newRowRegion;
        int newColRegion;
        return 0;
    }

    @Override
    public long opponent() {
        //TODO implement
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
