package Game_state.Region;

import Game_state.Player.Player;

public class Region_im implements Region {
    private final int location;
    private Player owner;
    private double deposit;
    public Region_im(int location){
        this.location = location;
        this.owner = null;
        this.deposit = 0;
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void updateOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public double getDeposit() {
        return this.deposit;
    }

    @Override
    public void updateDeposit(double money) {
        this.deposit += money;
    }

    @Override
    public int getLocation() {
        return this.location;
    }

    @Override
    public int Opponent() {
        return 0;
    }

    @Override
    public int nearby() {
        return 0;
    }
}
