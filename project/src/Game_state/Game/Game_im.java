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
    private int turn;

    public Game_im(Player p1, Player p2, List<Region> territory) {
        this.territory = territory;
        this.player1 = p1;
        this.player2 = p2;
        this.current_player = this.player1;
        this.turn = 1;
    }

    private boolean checkBudget() {
        return current_player.getBudget() >= actionCost;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return current_player.getIdentifiers();
    }

    @Override
    public boolean attack(Direction direction, long money) {
        if(checkBudget()) {
            current_player.updateBudget(-actionCost);
            if(current_player.getBudget() < money || money < 0) return false;
            int location = mockMove(direction, cityCrew.getLocation(), cityCrew.getCol());
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
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean collect(long money) {
        if(money < 0 || !checkBudget())
            return false;
        current_player.updateBudget(-actionCost);
        Region targetRegion = cityCrew;
        if(targetRegion.getOwner() != current_player)
            return false;
        if(money > targetRegion.getDeposit() || money <= 0){
            return false;
        }
        current_player.updateBudget(money);
        targetRegion.updateDeposit(-money);
        if(targetRegion.getDeposit() == 0)
            targetRegion.updateOwner(null);
        return true;
    }

    @Override
    public boolean invest(long money) {
        if(checkBudget()) {
            long invested = cityCrew.getDeposit();
            long max_dep = ReadData.getMaxDeposit();
            current_player.updateBudget(-actionCost);
            int check;
            int count = 0;
            if(cityCrew.getOwner()!= current_player) {
                for (int i = 0; i < 6; i++) {
                    check = mockMove(Direction.values()[i], cityCrew.getLocation(), cityCrew.getCol());
                    if (check == -1) {
                        count++;
                        continue;
                    }
                    if (territory.get(check).getOwner() != current_player) count++;
                }
                if (count == 6) return false;
            }
            if(current_player.getBudget() < money || money <= 0) return false;
            if(cityCrew.getOwner() != null && cityCrew.getOwner() != current_player) return false;
            if(invested == max_dep) return false;
            if(invested + money > max_dep){
                long invest = max_dep - invested;
                cityCrew.updateDeposit(invest);
                current_player.updateBudget(-invest);
            }else {
                cityCrew.updateDeposit(money);
                current_player.updateBudget(-money);
            }
            cityCrew.updateOwner(current_player);
            return true;
        }
        return false;
    }

    public int mockMove(Direction direction, int location , int col){
        int column = (int) ReadData.getCols();
        switch (direction) {
            case Up -> location -= column;
            case Down -> location += column;
            case UpLeft -> {
                if(col == 1) location = -1;
                else {
                    if (col % 2 == 0) location -= (column + 1);
                    else location--;
                }
            }
            case UpRight -> {
                if(col == column) location = -1;
                else {
                    if (col % 2 == 0) location += 1 - column;
                    else location++;
                }
            }
            case DownLeft -> {
                if(col == 1) location = -1;
                else {
                    if (col % 2 == 0) location--;
                    else location += column - 1;
                }
            }
            case DownRight -> {
                if(col == column) location = -1;
                else {
                    if (col % 2 == 0) location++;
                    else location += column + 1;
                }
            }
        }
        if(location < 0 || location >= territory.size()) return -1;
        else return location;
    }

    @Override
    public boolean move(Direction direction) {
        if(checkBudget()) {
            current_player.updateBudget(-actionCost);
            int location = mockMove(direction, cityCrew.getLocation(), cityCrew.getCol());
            if(location == -1) return false;
            if(territory.get(location).getOwner() != null && territory.get(location).getOwner() != current_player) return false;
            cityCrew = territory.get(location);
            return true;
        }
        return false;
    }

    @Override
    public boolean relocate() {
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
                    cityCrew.updateOwner(current_player);
                    current_player.updateCityCenter(cityCrew);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public long nearby(Direction direction) {
        long distance = 0;
        int location = mockMove(direction, cityCrew.getLocation(), cityCrew.getCol());
        Region targetRegion;
        while (location != -1) {
            targetRegion = territory.get(location);
            distance++;
            if (targetRegion.getOwner() != null && targetRegion.getOwner() != current_player)
                return ((distance) * 100 + (long) (Math.log10(targetRegion.getDeposit())) + 1);
            location = mockMove(direction, targetRegion.getLocation(), targetRegion.getCol());
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
                int mockLocation = mockMove(Direction.values()[i], (int) location, suffuse[i].getCol());
                if(mockLocation != -1)
                    suffuse[i] = territory.get(mockLocation);
                else suffuse[i] = null;
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

    public void endTurn() {
        if(current_player == this.player1){
            current_player = this.player2;
        }else{
            current_player = this.player1;
            CalculateInterest();
            turn++;
        }
    }

    private void CalculateInterest() {
        //the interest for the region in the current turn is d*r/100
        // d is the current deposit of a region
        // r is the interest rate percentage
        //the interest rate percentage r to be used is b * log10 d * ln t
        // b is the base interest rate percentage as specified in the configuration file
        // d is the current deposit of a region
        // t is the current turn count of a player
        long b = ReadData.getInterestRatePercentage();
        long max = ReadData.getMaxDeposit();
        for(Region region: territory) {
            long d = region.getDeposit();
            if(d == max || d == 0) continue;
            double r = b*Math.log10(d)*Math.log(turn);
            double i = d*r/100;
            region.updateDeposit(Math.round(i));
        }
    }

    public void beginTurn() {
        this.cityCrew = current_player.getCityCenter();
    }

    @Override
    public void sendPlan(String pathFile) throws IOException {
        Path fileName = Path.of(pathFile);
        String constructionPlan = Files.readString(fileName);
        Parser parser = new Parser_im(new Tokenizer_im(constructionPlan));
        List<Node.StateNode> nodes = parser.parse();
        beginTurn();
        for(Node.StateNode node : nodes){
            node.evaluate(this);
        }
        endTurn();
    }
}
