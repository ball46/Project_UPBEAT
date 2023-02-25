package Game_state.Region;

import Game_state.Player.Player;

public interface Region {
    Player getOwner();
    void updateOwner(Player owner);
    double getDeposit();
    void updateDeposit(double money);
    int getLocation();
    int Opponent();
    int nearby();
}
