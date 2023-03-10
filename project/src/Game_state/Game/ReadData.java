package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public static void getDataFile(String locate){
        Gson gson = new Gson();
        Path path = Path.of(locate);
        try(BufferedReader reader = Files.newBufferedReader(path)){
            Map data = gson.fromJson(reader, Map.class);
            for(Object key : data.keySet()){
                String swt = (String) key;
                long value = (long) Double.parseDouble(data.get(key).toString());
                switch (swt){
                    case "row" -> rows = value;
                    case "col" -> cols = value;
                    case "init_plan_min" -> initialPlanMinutes = value;
                    case "init_plan_sec" -> {
                        if(initialPlanSeconds < 0 || initialPlanSeconds > 59)
                            throw new GameError.ConfigurationError("Seconds must be between 0 and 59");
                        initialPlanSeconds = value;
                    }
                    case "init_budget" -> initialBudget = value;
                    case "init_center_dep" -> initialCenterDeposit = value;
                    case "plan_rev_min" -> planRevisionMinutes = value;
                    case "plan_rev_sec" -> {
                        if(planRevisionSeconds < 0 || planRevisionSeconds > 59)
                            throw new GameError.ConfigurationError("Seconds must be between 0 and 59");
                        planRevisionSeconds = value;
                    }
                    case "rev_cost" -> revisionCost = value;
                    case "max_dep" -> maxDeposit = value;
                    case "interest_pct" -> interestRatePercentage = value;
                    default -> throw new GameError.ConfigurationError("Wrong key");
                }
            }
        } catch (IOException e) {
            throw new GameError.DoNotSendFile(e.getMessage());
        }
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
