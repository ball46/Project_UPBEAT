package Game_state.Region;

import Game_state.Game.ReadData;

import java.util.List;

public class Row {
    private static List<Region> regions;
    private static final long rows = ReadData.getRows();
    private static long countRow;
    private static final int memberRows = (int) ReadData.getCols();
    public static List<Region> createRow(int count){
        if(count > rows) return null;
        countRow = count;
        for(int i = 1 + (memberRows * (count - 1)); i <= memberRows * count; i++){
            regions.add(new Region_im(i));
        }
        return regions;
    }
    public static long getCountRow(){return countRow;}
}
