package Game_state.Game;

import Game_state.Region.Region;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadDataTest {
    @Test
    public void testReadData() {
        assertDoesNotThrow(() -> ReadData.getDataFile("row = 1" + "col = 1" + "initPlanMin = 1"
                + "initPlanSec = 1" + "initBudget = 1" + "initCenterDep = 1" + "planRevMin = 1" + "planRevSec = 1"
                + "revCost = 1" + "maxDep = 1" + "interestPct = 1"));
        assertDoesNotThrow(() -> ReadData.getDataFile("row = 1" + "col = 1"));
        assertThrows(GameError.ConfigurationError.class, () -> ReadData.getDataFile("initPlanSec = 60"));
    }
    @Test
    public void testCreateMap() {
        Game game = ReadData.cretateGame("ball", "boss");
        List<Region> territory = game.getTerritory();
        assertEquals(ReadData.getRows()*ReadData.getCols(), territory.size());
        assertNotEquals(game.getPlayer1().getCityCenter(), game.getPlayer2().getCityCenter());
        assertEquals(game.getPlayer1().getCityCenter(), game.getCurrentPlayer().getCityCenter());
    }

    @Test
    public void testCreateNewRowAndCol() {
        ReadData.getDataFile("row = 20" + "col = 20");
        Game game = ReadData.cretateGame("ball", "boss");
        assertEquals(20*20, game.getTerritory().size());
    }
}