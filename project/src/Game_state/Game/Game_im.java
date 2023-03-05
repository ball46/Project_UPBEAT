package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import Grammar.AST.Node;
import Grammar.Parser.Parser;
import Grammar.Parser.Parser_im;
import Grammar.Tokenizer.Tokenizer_im;
import Type.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Game_im implements Game{
    private final Player player1, player2;
    private final List<Region> territory;
    private final long actionCost = 1;
    private Player current_player;
    private Region cityCrew;
    public Game_im(Player player1, Player p2, List<Region> territory) {
        this.territory = territory;
        this.player1 = player1;
        this.player2 = p2;
        this.current_player = this.player1;
    }

    private boolean checkBudget() {
        return current_player.getBudget() >= actionCost;
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
            int location = mockMove(direction, cityCrew.getLocation());
            if(location != -1) {
                Region targetRegion = territory.get(location);
                if (targetRegion != null) {
                    if (money < targetRegion.getDeposit()) {
                        targetRegion.updateDeposit(-money);
                    } else {
                        targetRegion.updateDeposit(-targetRegion.getDeposit());
                        targetRegion.updateOwner(null);
                    }
                    current_player.updateBudget(-money);
                }
            }
        }
    }

    @Override
    public boolean collect(long money) {
        if(money < 0 || !checkBudget())
            return false;
        current_player.updateBudget(-actionCost);
        Region targetRegion = cityCrew;
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

    public int mockMove(Direction direction, int location){
        int column = (int) ReadData.getCols();
        switch (direction) {
            case Up -> location -= column;
            case Down -> location += column;
            case UpLeft -> {
                if (location % 2 == 0) location -= (column + 1);
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
        if(location < 0 || location >= territory.size()) return -1;
        else return location;
    }

    @Override
    public boolean move(Direction direction) {
        if(checkBudget()) {
            current_player.updateBudget(-actionCost);
            int location = mockMove(direction, cityCrew.getLocation());
            if(location == -1) return false;
            if(territory.get(location).getOwner() != null && territory.get(location).getOwner() != current_player) return false;
            cityCrew = territory.get(location);
            return true;
        }
        return false;
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
                    current_player.getCityCenter().updateOwner(null);
                    cityCrew.updateOwner(current_player);
                    current_player.updateCityCenter(cityCrew);
                }
            }
        }
    }

    @Override
    public long nearby(Direction direction) {
        long distance = 0;
        int location = mockMove(direction, cityCrew.getLocation());
        Region targetRegion;
        while (location != 1) {
            targetRegion = territory.get(location);
            distance++;
            if (targetRegion.getOwner() != null && targetRegion.getOwner() != current_player)
                return ((distance) * 100 + (long) (Math.log10(targetRegion.getDeposit() + 1)) + 1);
            location = mockMove(direction, targetRegion.getLocation());
        }
        return 0L;
    }

    @Override
    public long opponent() {
        Region[] suffuse = new Region_im[6];
        long distance = 0;
        boolean stop;
        Arrays.fill(suffuse, cityCrew);
        do {
            for(int i = 0 ; i < 6; i++){
                if(suffuse[i] == null) continue;
                long location = suffuse[i].getLocation();
                Player player = territory.get((int) location).getOwner();
                if(player != null && player != current_player)
                    return i + 1L + (distance * 10L);
                suffuse[i] = territory.get(mockMove(Direction.values()[i], (int) location));
            }
            stop = true;
            for(Region region : suffuse) stop = stop && (region == null);
            distance++;
        } while (!stop);
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
    public Region getCityCrew() {
        return cityCrew;
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

    @Override
    public void beginTurn() {
        this.cityCrew = current_player.getCityCenter();
    }

    @Override
    public void sendPlan(String pathFile) throws IOException {
        Path fileName = Path.of(pathFile);
        String constructionPlan = Files.readString(fileName);
        Parser parser = new Parser_im(new Tokenizer_im(constructionPlan));
        Node.StateNode node = parser.parse();
        beginTurn();
        while(node!= null){
            node = node.evaluate(this);
        }
        endTurn();
    }
}
