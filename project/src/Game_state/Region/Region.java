package Game_state.Region;

public interface Region {
    String getOwner();
    void updateOwner(String owner);
    double getBudget();
    void updateBudget(double money);
    int[] getLocation();
    int Opponent();
    int nearby();
}
