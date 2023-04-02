package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import Grammar.AST.Node;
import Grammar.AST.State.AssignmentNode;
import Grammar.Parser.ParseConfig;
import Grammar.Parser.Parser;
import Grammar.Parser.ParserError;
import Grammar.Tokenizer.Tokenizer_im;

import java.util.*;

public class ReadData {//This is default data

    private static long rows = 9;

    private static long cols = 9;

    private static long initialPlanMinutes;//number of minutes to initial construction plan

    private static long initialPlanSeconds;

    private static long initialBudget;//is my money

    private static long initialCenterDeposit;//is my money in cityCenter

    private static long planRevisionMinutes;//number of minutes to revisions construction plan

    private static long planRevisionSeconds;

    private static long revisionCost;//cost to revise plan

    private static long maxDeposit;//maximum deposit for each region

    private static long interestRatePercentage;

    private static List<Region> territory;

    public static void getDataFile(String config){
        Parser parser = new ParseConfig(new Tokenizer_im(config));
        List<Node.StateNode> nodes = parser.parse();
        Map<String, Long> map = new HashMap<>();
        for(Node.StateNode node : nodes){
            if(!(node instanceof AssignmentNode))
                throw new ParserError.CommandNotFound(node.toString());
            ((AssignmentNode) node).evaluate(map);
        }
        rows = map.getOrDefault( "row", 9L);
        cols = map.getOrDefault("col", 9L);
        initialPlanMinutes = map.getOrDefault("initPlanMin", 5L);
        initialPlanSeconds = map.getOrDefault("initPlanSec", 0L);
        initialBudget = map.getOrDefault("initBudget", 10000L);
        initialCenterDeposit = map.getOrDefault("initCenterDep", 100L);
        planRevisionMinutes = map.getOrDefault("planRevMin", 30L);
        planRevisionSeconds = map.getOrDefault("planRevSec", 0L);
        revisionCost = map.getOrDefault("revCost", 100L);
        maxDeposit = map.getOrDefault("maxDep", 1000000L);
        interestRatePercentage = map.getOrDefault("interestPct", 5L);
        if(rows <= 0) throw  new GameError.ConfigurationError("rows must be greater than 0");
        if(cols <= 0) throw new GameError.ConfigurationError("column must be greater than 0");
        if(initialPlanSeconds >= 60) throw new GameError.ConfigurationError("Seconds is between 0 - 59");
        if(planRevisionSeconds >= 60) throw new GameError.ConfigurationError("Seconds is between 0 - 59");
    }

    public static Game cretateGame(String p1, String p2){
        List<Region> territory = createMap();
        Player player1 = createPlayer(p1);
        Player player2 = createPlayer(p2);
        return new Game_im(player1, player2, territory);
    }

    public static List<Region> createMap(){
        territory = new ArrayList<>();
        for(int i = 0; i < rows * cols; i++){
            Region region = new Region_im(i);
            int col = i % (int) cols != 0 ? (i % (int) cols) + 1 : 1;
            int row = (i / (int) cols) + 1;
            region.AddAddress(row, col);
            territory.add(region);
        }
        return territory;
    }

    public static Region randomCityCenter(){
        Region region;
        Random random = new Random();
        do {
            int location = random.nextInt(territory.size());
            region = territory.get(location);
        }while (region.getOwner() != null);
        return region;
    }

    public static Player createPlayer(String name){
        if(territory == null || territory.size() == 0) return null;
        Region region = randomCityCenter();
        Player player = new Player_im(name, initialBudget, region);
        region.updateOwner(player);
        region.updateDeposit(initialCenterDeposit);
        return player;
    }

    public static long getRows() {return rows;}

    public static long getCols() {return cols;}

    public static long getInitialBudget() {return initialBudget;}

    public static long getMaxDeposit() {return maxDeposit;}

    public static long getInterestRatePercentage() {return interestRatePercentage;}

    public static long getInitialDeposit() { return initialCenterDeposit;}
    public static long getRevisionCost() {return revisionCost;}
}
