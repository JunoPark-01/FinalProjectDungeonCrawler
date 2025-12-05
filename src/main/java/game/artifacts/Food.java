package game.artifacts;

public class Food {
    private final String name;
    private final Double healthValue;

    public Food(String name, Double healthValue){
        this.name = name;
        this.healthValue = healthValue;
    }

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", healthValue);
        return name + "(" + formattedHealth + ")";
    }
}
