package Game_state.Region;

import Game_state.Game.ReadData;
import Game_state.Player.Player;

public class Region_im implements Region {
    private final int location;
    private Player owner;
    private long deposit;
    private final long max_deposit = ReadData.getMaxDeposit();
    private  int row, col;
    public Region_im(int location) {
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
    public long getDeposit() {
        return this.deposit;
    }

    @Override
    public void updateDeposit(long money) {
        this.deposit = Math.max(0, this.deposit + money);
        this.deposit = Math.min(this.deposit, max_deposit);
    }

    @Override
    public int getLocation() {
        return this.location;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void AddAddress(int row, int col) {
        this.row = row;
        this.col = col;
    }

}
