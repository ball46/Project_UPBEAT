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
    int col = (int) ReadData.getCols();
    int row = (int) ReadData.getRows();
    @Test
    public void testMove( ){
        //test move player 1
        int locationCurrentPlayer = p1.getCityCenterLocation();
        game.beginTurn();
        if(game.move(Direction.Up)) {
            assertEquals(locationCurrentPlayer - col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.UpRight)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(locationCurrentPlayer + 1 - col, game.getCityCrew().getLocation());
            else assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.DownRight)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col + 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.Down)) {
            assertEquals(locationCurrentPlayer + col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.DownLeft)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col - 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.UpLeft)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(locationCurrentPlayer - (col + 1), game.getCityCrew().getLocation());
            else assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
        }

        //test move player 2
        game.endTurn();
        game.beginTurn();
        locationCurrentPlayer = p2.getCityCenterLocation();
        if(game.move(Direction.Up)) {
            assertEquals(locationCurrentPlayer - col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.UpRight)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(locationCurrentPlayer + 1 - col, game.getCityCrew().getLocation());
            else assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.DownRight)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col + 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.Down)) {
            assertEquals(locationCurrentPlayer + col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.DownLeft)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col - 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.UpLeft)) {
            if (locationCurrentPlayer % 2 == 0)
                assertEquals(locationCurrentPlayer - (col + 1), game.getCityCrew().getLocation());
            else assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
        }
    }
}