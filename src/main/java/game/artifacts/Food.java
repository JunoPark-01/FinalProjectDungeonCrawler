package game.artifacts;

public record Food(String name, Double healthValue){

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", healthValue);
        return name + "(" + formattedHealth + ")";
    }
}
