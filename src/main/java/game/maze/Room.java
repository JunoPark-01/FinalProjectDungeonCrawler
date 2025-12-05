package game.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import game.artifacts.*;
import game.characters.Character;

public class Room {

    static private final Random rand = new Random();

    private final String name;
    private final List<Room> neighbors = new ArrayList<>();
    private final List<Character> characters = new ArrayList<>();
    private final List<Food> foodItems = new ArrayList<>();
    private final List<Weapon> weaponItems = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Character> getLivingMonsters() {
        return getLivingCharacters().stream()
                .filter(Character::isMonster)
                .toList();
    }

    public List<Character> getLivingPlayer() {
        return getLivingCharacters().stream()
                .filter(Character::isPlayer)
                .toList();
    }

    public List<String> getContents() {
        List<String> characterStrings = new ArrayList<>(getLivingCharacters().stream()
                .map(Object::toString)
                .toList());

        for (Food foodItem : foodItems) {
            characterStrings.add(foodItem.toString());
        }

        return characterStrings;
    }

    void addNeighbor(Room neighbor) {
        // Make sure we are never a neighbor of ourselves
        if (this != neighbor) {
            this.neighbors.add(neighbor);
        }
    }

    void addNeighbor(Room neighbor, boolean bidirectional) {
        this.addNeighbor(neighbor);
        if (bidirectional) {
            neighbor.addNeighbor(this);
        }
    }

    public String toString() {
        String representation = "\t" + name + ":\n\t\t";
        representation += String.join("\n\t\t", getContents());
        representation += "\n";
        return representation;
    }

    void add(Character character) {
        characters.add(character);
        character.enterRoom(this);
    }

    public Boolean hasLivingMonsters() {
        return getLivingCharacters().stream().anyMatch(Character::isMonster);
    }

    public Boolean hasLivingPlayer() {
        return getLivingCharacters().stream().anyMatch(Character::isPlayer);
    }

    public void remove(Character character) {
        characters.remove(character);
    }

//    public Character getRandomAdventurer() {
//        List<Character> adventurers = getLivingAdventurers();
//        return adventurers.get(rand.nextInt(adventurers.size()));
//    }

    public Room getRandomNeighbor() {
        if (neighbors.isEmpty()) {
            return null;
        }
        return neighbors.stream().toList().get(rand.nextInt(neighbors.size()));
    }

    public void enter(Character character) {
        add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(character -> !character.isDead())
                .toList();
    }

    public boolean hasNeighbor(Room neighbor) {
        return neighbors.contains(neighbor);
    }

    public int numberOfNeighbors() {
        return neighbors.size();
    }

    public boolean hasNeighbors() {
        return !neighbors.isEmpty();
    }

    public boolean contains(Character character) {
        return characters.contains(character);
    }

    public boolean contains(Food foodItem) {
        return foodItems.contains(foodItem);
    }

    public void add(Food food) {
        foodItems.add(food);
    }

    public void add(Weapon weapon){weaponItems.add(weapon);}

    public boolean hasFood() {
        return !foodItems.isEmpty();
    }

    public Optional<Food> getFood() {
        Optional<Food> food = foodItems.stream().findAny();
        if (food.isPresent()) {
            foodItems.remove(food.get());
        }
        return food;
    }

    public List<Food> getFoodItems() {
        return foodItems.stream().toList();
    }

    public Optional<Character> getCreature() {
        List<Character> monsters = getLivingMonsters();
        if (monsters.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(monsters.get(rand.nextInt(monsters.size())));
    }

    public List<Room> getNeighbors() {
        return neighbors;
    }

    public List<Weapon> getWeapons() {
        return weaponItems;
    }
}
