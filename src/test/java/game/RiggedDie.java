package game;

public class RiggedDie implements Die {
    private final int value;

    public RiggedDie(int roll) {
        this.value = roll;
    }

    @Override
    public int roll() {
        return value;
    }
}
