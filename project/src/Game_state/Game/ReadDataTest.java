package Game_state.Game;

import Game_state.Player.Player;
import Game_state.Region.Region;
import Game_state.Region.Row;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadDataTest {
    @Test
    public void testReadData() {
        String locate = "D:\\UPBEAT\\project\\src\\Data\\TestRead.json";
        assertDoesNotThrow(() -> ReadData.getDataFile(locate));
    }
    @Test
    public void testCreateMap() {
        String locate = "D:\\UPBEAT\\project\\src\\Data\\TestMap.json";
        ReadData.getDataFile(locate);
        long sizeMap = ReadData.getRows() * ReadData.getCols();
        assertEquals(sizeMap, ReadData.createMap().size());
    }

    @Test
    public void testCreatePlayer() {
        String locate = "D:\\UPBEAT\\project\\src\\Data\\TestPlayer.json";
        assertNull(ReadData.createPlayer("ball"));

//        //set row and col = 0 in TestPlayer.json
//        ReadData.getDataFile(locate);
//        ReadData.createMap();
//        assertNull(ReadData.createPlayer("ball"));

//        //set row and col = 1 in TestPlayer.json
//        ReadData.getDataFile(locate);
//        ReadData.createMap();
//        assertNotNull(ReadData.createPlayer("ball"));

        //set row and col = 5 in TestPlayer.json
        ReadData.getDataFile(locate);
        ReadData.createMap();
        assertNotNull(ReadData.createPlayer("ball"));
    }

    @Test
    public void testCreateMap2(){
        String locate = "D:\\UPBEAT\\project\\src\\Data\\TestRead.json";
        ReadData.getDataFile(locate);
        List<List<Region>> territory = ReadData.createMap2();
        Player player = ReadData.createPlayer("ball");
        assertEquals(1, territory.get(player.getCityCenterRow()).get(player.getCityCenterLocation()));
    }
}