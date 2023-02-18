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
        this.player1 = Configuration.createPlayer("ball");
        this.player2 = Configuration.createPlayer("boss");
        this.territory = Configuration.createMap();
        identifiers = new HashMap<>();
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifiers;
    }

    @Override
    public Player getPlayer() {
        return player1;
    }

    @Override
    public Region getRegion() {
        return null;
    }

    @Override
    public List<Region> getTerritory() {
        return territory;
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
