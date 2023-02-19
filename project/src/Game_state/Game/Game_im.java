package Game_state.Game;

import Game_state.Player.*;
import Game_state.Region.*;
import Type.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game_im implements Game{
    private final Player player1;
    private final Player player2;
    private final List<Region> territory;
    private final Map<String, Long> identifiers;
    public Game_im() {
        this.player1 = ReadData.createPlayer("ball");
        this.player2 = ReadData.createPlayer("boss");
        this.territory = ReadData.createMap();
        identifiers = new HashMap<>();
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
}
