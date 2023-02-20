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

public class ReadData {
    private static int row;
    private static int col;
    private static int init_plan_min;//number of minutes to initial construction plan
    private static int init_plan_sec;
    private static double init_budget;//is my money
    private static double init_center_dep;//is my money in city center
    private static int plan_rev_min;//number of minutes to revisions construction plan
    private static int plan_rev_sec;
    private static int rev_cost;//cost to revise plan
    private static double max_dep;//maximum deposit for each region
    private static double interest_pct;
    private static List<Region> territory;
    public static void getDataFile(){
        Gson gson = new Gson();
        Path path = Path.of("D:\\UPBEAT\\project\\src\\Data\\ConfigFile.json");
        try(BufferedReader reader = Files.newBufferedReader(path)){
            Map data = gson.fromJson(reader, Map.class);
            row = (int) data.get("row");
            col = (int) data.get("col");
            init_plan_min = (int) data.get("init_plan_min");
            init_plan_sec = (int) data.get("init_plan_sec");
            init_budget = (double) data.get("init_budget");
            init_center_dep = (double) data.get("init_center_dep");
            plan_rev_min = (int) data.get("plan_rev_min");
            plan_rev_sec = (int) data.get("plan_rev_sec");
            rev_cost = (int) data.get("rev_cost");
            max_dep = (double) data.get("max_dep");
            interest_pct = (double) data.get("interest_pct");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Region> createMap(){
        territory = new ArrayList<>();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                territory.add(new Region_im(i,j));
            }
        }
        return territory;
    }

    public static Player createPlayer(String name){
        return new Player_im(name,init_budget,init_center_dep);
    }
}
