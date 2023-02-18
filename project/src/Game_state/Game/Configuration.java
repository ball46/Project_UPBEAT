package Game_state.Game;

import Game_state.Region.Region;

import java.util.List;

public class Configuration {
    private static final int row = 20;
    private static final int col = 15;
    private static final int plan_min = 5;//number of minutes to initial construction plan
    private static final int plan_sec = 0;
    private static final double budget = 10_000;//is my money
    private static final double center_dep = 100;//is my money in city center
    private static final int plan_rev_min = 30;//number of minutes to revisions construction plan
    private static final int plan_rev_sec = 0;
    private static final int rev_cost = 100;//cost to revise plan
    private static final double max_dep = 1_000_000;//maximum deposit for each region
    private static final double interest_pct = 5;
    private List<Region> territory;
    public static void getPlanFile(){
        //I don't know to implement this, but it works to load the configuration file
    }
    public static List<Region> gameStart(){
        //I don't know to do this
        //creates new game and set everything like as configuration file
        return null;
    }
}
