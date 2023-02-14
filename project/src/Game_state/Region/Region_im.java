package Game_state.Region;

public class Region_im implements Region {
    private int[] location;
    private String owner;
    private double budget;
    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void updateOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public double getBudget() {
        return budget;
    }

    @Override
    public void updateBudget(double money) {
        this.budget += money;
    }

    @Override
    public int[] getLocation() {
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
