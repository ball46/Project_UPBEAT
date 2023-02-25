package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import Type.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game_im implements Game{
    private final Player player1, player2;
    private Player current_player;
    private final List<Region> territory;
    private final Map<String, Long> identifiers;
    public Game_im(String nameP1, String nameP2) {
        this.player1 = ReadData.createPlayer(nameP1);
        this.player2 = ReadData.createPlayer(nameP2);
        this.territory = ReadData.createMap();
        this.identifiers = new HashMap<>();
        this.current_player = this.player1;
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifiers;
    }

    @Override
    public void attack(Direction direction, long money) {

    }

    @Override
    public void collect(long money) {

    }

    @Override
    public void invest(long money) {

    }

    @Override
    public void move(Direction direction) {

    }

    @Override
    public void relocate() {

    }

    @Override
    public long nearby(Direction direction) {
        return 0;
    }

    @Override
    public long opponent() {
        return 0;
    }

    @Override
    public void endTurn() {
        if(current_player == this.player1){
            current_player = this.player2;
        }else{
            current_player = this.player1;
        }
    }
}
