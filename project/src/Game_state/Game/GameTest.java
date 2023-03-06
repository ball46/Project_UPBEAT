package Game_state.Game;

import Game_state.Player.Player;
import Game_state.Player.Player_im;
import Game_state.Region.Region;
import Type.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class GameTest {
    List<Region> territory = ReadData.createMap();
    Player p1 = new Player_im("p1", ReadData.getInitialBudget(), territory.get(39));
    Player p2 = new Player_im("p2", ReadData.getInitialBudget(), territory.get(50));
    Game_im game = new Game_im(p1, p2, territory);
    int col = (int) ReadData.getCols();
    @BeforeEach
    public void addMoneyInCityCenter(){
        territory.get(39).updateOwner(p1);
        territory.get(39).updateDeposit(ReadData.getInitialDeposit());
        territory.get(50).updateOwner(p2);
        territory.get(50).updateDeposit(ReadData.getInitialDeposit());
    }

    @Test
    public void testMoveBothOfAllPlayer1AndPlayer2( ){
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

    @Test
    public void testMoveItCanNotMoveInEnemyRegion(){
        game.beginTurn();

    }
    @Test
    public void testInvestInAnotherRegion(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        long action = 1;
        if(game.move(Direction.Up)) {
            game.invest(investment);
            assertEquals(game.getCityCrew().getDeposit(), investment);
            assertEquals(p1.getBudget(),  money - investment - 2*action);
        }
    }

    @Test
    public void testInvestInCityCenter(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        long action = 1;
        game.invest(investment);
        assertEquals(game.getCityCrew().getDeposit(), investment + 100);
        assertEquals(p1.getBudget(),  money - investment - action);
    }

    @Test
    public void testInvestZero(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 0;
        long action = 1;
        if(game.move(Direction.Up)) {
            game.invest(investment);
            assertEquals(game.getCityCrew().getDeposit(), investment);
            assertEquals(p1.getBudget(),  money - investment - 2*action);
            assertNull(game.getCityCrew().getOwner());
        }
    }

    @Test
    public void testCollectInCityCenter(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long collect = 100;
        long action = 1;
        if(game.collect(collect)) {
            assertEquals(game.getCityCrew().getDeposit(), p1.getCityCenter().getDeposit());
            assertEquals(p1.getBudget(), money + collect - action);
        }
    }

    @Test
    public void testCollectZero(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        long collect = 0;
        long action = 1;
        if(game.move(Direction.Up)) {
            game.invest(investment);
            game.collect(collect);
            assertEquals(game.getCityCrew().getDeposit(), investment);
            assertEquals(p1.getBudget(),  money - investment - 3*action);
        }
    }

    @Test
    public void testCollectInAnotherRegionAndCollectItToZero(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        long collect = 100;
        long action = 1;
        if(game.move(Direction.Up)) {
            game.invest(investment);
            assertEquals(game.getCityCrew().getDeposit(), investment);
            assertEquals(game.getCityCrew().getOwner(), p1);
            assertEquals(p1.getBudget(),  money - investment - 2*action);
            game.collect(collect);
            assertEquals(p1.getBudget(),  money - 3*action);
            assertNull(game.getCityCrew().getOwner());
        }
    }

    @Test
    public void testRelocate( ){
        game.beginTurn();
        int location = game.getCityCrew().getLocation();
        int counter = 0;
        for(int i = 0; i < 4; i++) if(game.move(Direction.Up)) counter++;
        if(counter == 0)
            for(int i = 0; i < 4; i++) if(game.move(Direction.Down)) counter++;
        game.invest(100);
        game.relocate();
        assertEquals(game.getCityCrew(), p1.getCityCenter());
        assertNull(territory.get(location).getOwner());
    }

    @Test
    public void testAttack() {
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        long action = 1;
        if(game.move(Direction.Up)) {
            game.invest(investment);
            assertEquals(game.getCityCrew().getDeposit(), investment);
            assertEquals(p1.getBudget(),  money - investment - 2*action);
            game.move(Direction.Down);
            game.attack(Direction.Up, investment);
            game.move(Direction.Up);
            assertNull(game.getCityCrew().getOwner());
        }
    }

    @Test
    public void testNearby(){

    }

    @Test
    public void testOpponent(){

    }
}