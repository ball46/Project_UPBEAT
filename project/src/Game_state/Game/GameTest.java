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
    long action = 1;
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
        int CityCenterP1 = p1.getCityCenterLocation();
        game.beginTurn();
        if(game.move(Direction.Up)) {
            assertEquals(locationCurrentPlayer - col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        int locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.UpRight)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(locationCurrentPlayer + 1 - col, game.getCityCrew().getLocation());
            else assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.DownRight)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col + 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.Down)) {
            assertEquals(locationCurrentPlayer + col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.DownLeft)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col - 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.UpLeft)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(locationCurrentPlayer - (col + 1), game.getCityCrew().getLocation());
            else assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
        }
        assertEquals(CityCenterP1, locationCurrentPlayer);

        //test move player 2
        game.endTurn();
        game.beginTurn();
        locationCurrentPlayer = p2.getCityCenterLocation();
        int CityCenterP2 = p2.getCityCenterLocation();
        if(game.move(Direction.Up)) {
            assertEquals(locationCurrentPlayer - col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.UpRight)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(locationCurrentPlayer + 1 - col, game.getCityCrew().getLocation());
            else assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.DownRight)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(++locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col + 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        if(game.move(Direction.Down)) {
            assertEquals(locationCurrentPlayer + col, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.DownLeft)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
            else assertEquals(locationCurrentPlayer + col - 1, game.getCityCrew().getLocation());
        }

        locationCurrentPlayer = game.getCityCrew().getLocation();
        locationCurrentCol = game.getCityCrew().getCol();
        if(game.move(Direction.UpLeft)) {
            if (locationCurrentCol % 2 == 0)
                assertEquals(locationCurrentPlayer - (col + 1), game.getCityCrew().getLocation());
            else assertEquals(--locationCurrentPlayer, game.getCityCrew().getLocation());
        }
        assertEquals(CityCenterP2, locationCurrentPlayer);
    }

    @Test
    public void testMoveItCanNotMoveInEnemyRegion(){
        game.beginTurn();
        assertTrue(game.move(Direction.DownRight));
        game.move(Direction.DownRight);
        assertFalse(game.move(Direction.DownRight));
    }

    @Test
    public void testMoveItCanNotMoveOutOfMap(){
        game.beginTurn();
        int walk;
        for(int i = 0; i < 5; i++){
            walk = game.mockMove(Direction.Up, game.getCityCrew().getLocation(), game.getCityCrew().getCol());
            if(walk == -1){
                assertFalse(game.move(Direction.Up));
            }else game.move(Direction.Up);
        }
    }
    @Test
    public void testInvestInAnotherRegion(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        if(game.move(Direction.Up)) {
            if(game.invest(investment)) {
                assertEquals(game.getCityCrew().getDeposit(), investment);
                assertEquals(p1.getBudget(), money - investment - 2 * action);
            }
        }
    }

    @Test
    public void testInvestInCityCenter(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        if( game.invest(investment)) {
            assertEquals(game.getCityCrew().getDeposit(), investment + 100);
            assertEquals(p1.getBudget(), money - investment - action);
        }
    }

    @Test
    public void testInvestZero(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 0;
        if(game.move(Direction.Up)) {
            if(game.invest(investment)) {
                assertEquals(game.getCityCrew().getDeposit(), investment);
                assertEquals(p1.getBudget(), money - investment - 2 * action);
                assertNull(game.getCityCrew().getOwner());
            }
        }
    }

    @Test
    public void testCollectInCityCenter(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long collect = 100;
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
        for(int i = 0; i < 4; i++) if(game.move(Direction.Up)) {
            game.invest(100);
        }
        if(game.relocate()) {
            assertEquals(game.getCityCrew(), p1.getCityCenter());
            assertNull(territory.get(location).getOwner());
        }
    }

    @Test
    public void testAttackInMyRegion() {
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long investment = 100;
        if(game.move(Direction.Up)) {
            game.invest(investment);
            assertEquals(game.getCityCrew().getDeposit(), investment);
            assertEquals(p1.getBudget(),  money - investment - 2*action);
            game.move(Direction.Down);
            if(game.attack(Direction.Up, investment)) {
                game.move(Direction.Up);
                assertNull(game.getCityCrew().getOwner());
            }
        }
    }

    @Test
    public void testAttackToRegionIsNoOwner(){
        game.beginTurn();
        long money = ReadData.getInitialBudget();
        long attack = 100;
        if(game.attack(Direction.Up, attack)) {
            assertEquals(p1.getBudget(), money - attack - action);
            game.move(Direction.Up);
            assertNull(game.getCityCrew().getOwner());
        }
    }

    @Test
    public void testAttackToEnemyRegion(){
        game.beginTurn();
        long investment = 100;
        long attack = 50;
        game.move(Direction.DownRight);
        game.invest(investment);
        game.endTurn();
        game.beginTurn();
        if(game.attack(Direction.UpLeft, attack)) {
            assertNotNull(territory.get(40).getOwner());
            assertEquals(territory.get(40).getOwner(), p1);
        }
        if(game.attack(Direction.UpLeft, attack))
            assertNull(territory.get(40).getOwner());
    }

    @Test
    public void testNearby(){
        game.beginTurn();
        long dis = game.nearby(Direction.DownRight);
        assertEquals(203, dis);
        game.move(Direction.UpLeft);
        dis = game.nearby(Direction.DownRight);
        assertEquals(303, dis);
        game.move(Direction.UpRight);
        game.move(Direction.UpRight);
        game.move(Direction.UpRight);
        dis = game.nearby(Direction.Down);
        assertEquals(303, dis);
        game.move(Direction.DownRight);
        game.move(Direction.DownRight);
        game.move(Direction.DownRight);
        dis = game.nearby(Direction.DownLeft);
        assertEquals(303, dis);
        game.move(Direction.Down);
        for(int i = 0; i < 6; i++){
            assertEquals(0,game.nearby(Direction.values()[i]));
        }
    }

    @Test
    public void testOpponent(){
        game.beginTurn();
        assertEquals(23, game.opponent());
        game.move(Direction.UpRight);
        assertEquals(0, game.opponent());
        game.move(Direction.UpRight);
        assertEquals(24, game.opponent());
        game.move(Direction.DownRight);
        assertEquals(0, game.opponent());
        game.move(Direction.DownRight);
        assertEquals(25, game.opponent());
        game.move(Direction.Down);
        assertEquals(0, game.opponent());
        game.move(Direction.Down);
        assertEquals(26, game.opponent());
        game.move(Direction.DownLeft);
        assertEquals(0, game.opponent());
        game.move(Direction.DownLeft);
        assertEquals(21, game.opponent());
        game.move(Direction.UpLeft);
        assertEquals(0, game.opponent());
        game.move(Direction.UpLeft);
        assertEquals(22, game.opponent());
    }
}