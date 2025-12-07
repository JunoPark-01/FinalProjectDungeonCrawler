package game;

import java.util.Random;

public class RandomDie implements Die {
    static private final Random rand = new Random();
    private final int sides;

    public RandomDie(int sides) {
        this.sides = sides;
    }

    public static Die sixSided() {
        return new RandomDie(6);
    }

    public static Die fourSided() {
        return new RandomDie(4);
    }

    public static Die twelveSided() {
        return new RandomDie(12);
    }

    public static Die twentySided() {
        return new RandomDie(20);
    }

    public int roll() {
        return rand.nextInt(sides) + 1;
    }

    public String toString(){
        return sides+" sided die";
    }
}
