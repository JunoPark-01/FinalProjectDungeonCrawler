package game.artifacts;

import java.util.Random;

public class FoodFactory {
    private static final Random random = new Random();

    private static final double BASIC_FOOD_VALUE = 1.0;
    private static final double GOOD_FOOD_VALUE = 3.0;
    private static final double GREAT_FOOD_VALUE = 5.0;

    private static final String[] BASIC_FOOD_NAMES = new String[]{
            "nuts", "fig", "berries", "raw egg", "bugs", "moldy bread", "worm", "rotten apple"};

    private static final String[] GOOD_FOOD_NAMES = new String[]{
            "bread", "apple", "drumstick", "jerky", "orange", "milk", "sandwich", "cupcake"};

    private static final String[] GREAT_FOOD_NAMES = new String[]{
            "steak", "cake", "hamburger", "golden apple", "BLT", "milkshake", "pancakes", "lasagna"};

    private static String getRandomBasicFoodName() {
        return BASIC_FOOD_NAMES[random.nextInt(BASIC_FOOD_NAMES.length)];
    }

    private static String getRandomGoodFoodName() {
        return GOOD_FOOD_NAMES[random.nextInt(GOOD_FOOD_NAMES.length)];
    }

    private static String getRandomGreatFoodName() {
        return GREAT_FOOD_NAMES[random.nextInt(GREAT_FOOD_NAMES.length)];
    }

    public Food createFood(String name, double healthValue) {
        return new Food(name, healthValue);
    }

    public Food createBasicFood() {
        return createFood(getRandomBasicFoodName(), BASIC_FOOD_VALUE);
    }

    public Food createGoodFood() {
        return createFood(getRandomGoodFoodName(), GOOD_FOOD_VALUE);
    }

    public Food createGreatFood() {
        return createFood(getRandomGreatFoodName(), GREAT_FOOD_VALUE);
    }

//    public List<Food> createFood(int numberOfItems) {
//        return IntStream.range(0, numberOfItems)
//                .mapToObj(_ -> createFood())
//                .toList();
//    }
}
