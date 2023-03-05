package Game_state.Game;

import Game_state.Player.Player;
import Game_state.Region.Region;
import Type.Direction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    List<Region> territory = ReadData.createMap();
    Player p1 = ReadData.createPlayer("p1");
    Player p2 = ReadData.createPlayer("p2");
    Game game = new Game_im(p1, p2, territory);
    @Test
    public void testMockMove( ){
        int locationP1 = p1.getCityCenterLocation();
        int locationP2 = p2.getCityCenterLocation();
        game.beginTurn();
        game.move(Direction.Up);
        assertEquals(locationP1 - ReadData.getCols() , game.getCityCrew().getLocation());
        game.endTurn();
        game.beginTurn();
        game.move(Direction.Down);
        assertEquals(locationP2 + ReadData.getCols() , game.getCityCrew().getLocation());
    }
}