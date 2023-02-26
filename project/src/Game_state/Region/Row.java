package Game_state.Region;

import Game_state.Game.ReadData;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<Region> regions = new ArrayList<>();
    private final long rows = ReadData.getRows();
    private long countRow;
    private final int memberRows = (int) ReadData.getCols();
    public List<Region> createRow(int count){
        if(count > rows) return null;
        countRow = count;
        for(int i = 1 + (memberRows * (count - 1)); i <= memberRows * count; i++){
            regions.add(new Region_im(i));
        }
        return regions;
    }
    public long getCountRow(){return countRow;}
}
