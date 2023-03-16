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

    private static long initialPlanMinutes = 5;//number of minutes to initial construction plan

    private static long initialPlanSeconds = 0;

    private static long initialBudget = 10000;//is my money

    private static long initialCenterDeposit = 100;//is my money in cityCenter

    private static long planRevisionMinutes = 30;//number of minutes to revisions construction plan

    private static long planRevisionSeconds = 0;

    private static long revisionCost = 100;//cost to revise plan

    private static long maxDeposit = 1000000;//maximum deposit for each region

    private static long interestRatePercentage = 5;

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
        rows = map.getOrDefault("row", rows);
        cols = map.getOrDefault("col", cols);
        initialPlanMinutes = map.getOrDefault("init_plan_min", initialPlanMinutes);
        initialPlanSeconds = map.getOrDefault("init_plan_sec", initialPlanSeconds);
        initialBudget = map.getOrDefault("init_budget", initialBudget);
        initialCenterDeposit = map.getOrDefault("init_center_dep", initialCenterDeposit);
        planRevisionMinutes = map.getOrDefault("plan_rev_min", planRevisionMinutes);
        planRevisionSeconds = map.getOrDefault("plan_rev_sec", planRevisionSeconds);
        revisionCost = map.getOrDefault("rev_cost", revisionCost);
        maxDeposit = map.getOrDefault("max_dep", maxDeposit);
        interestRatePercentage = map.getOrDefault("interest_pct", interestRatePercentage);
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
}
