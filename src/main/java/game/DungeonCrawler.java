package game;

import game.artifacts.Food;
import game.artifacts.Weapon;
import game.characters.Character;
import game.maze.*;
import game.characters.*;
import java.util.Scanner;

import java.util.List;
import java.util.Random;

public class DungeonCrawler {
    Maze maze;
    int turnCount = 0;
    final Random randomness = new Random();
    Scanner scanner = new Scanner(System.in);

    public DungeonCrawler(Maze maze){this.maze = maze;}

    public String toString() {
        return "Dungeon Crawler: turn " + turnCount + "\n" + maze.toString();
    }

    public Boolean isOver() {
        return !hasLivingMonsters() || !hasLivingPlayer();
    }

    public Boolean hasLivingMonsters() {
        return maze.hasLivingMonsters();
    }

    public Boolean hasLivingPlayer() {
        return maze.hasLivingPlayer();
    }

    private void playTurn() {
        if (turnCount == 0) {
            System.out.println("\n...Building Dungeon...\n");
        }
        turnCount += 1;

        // Process all the characters in random order
        List<Character> characters = getLivingCharacters();
        while (!characters.isEmpty()) {
            int index = randomness.nextInt(characters.size());
            Character character = characters.get(index);
            if(character.isPlayer()) { //player
                if (!character.isDead()) {
                    displayOptions(character.getCurrentLocation());
                    //TODO cin
                    System.out.println("Choose an option");
                    String input = scanner.nextLine();
                    while (validOption(input)) {
                        System.out.println("Please pick a valid option");
                        input = scanner.nextLine();
                    }
                    character.doAction(input);
                    System.out.println("You decided to "+input);
                }
            }else{  //monster
                if(!character.isDead()){
                    character.doAction();
                    System.out.println("Monster "+character.getName()+" moved");

                }
            }
            characters.remove(index);
        }
    }

    private void displayOptions(Room room){
        int numberOfOptions = 1;
        for(Room currentRoom : room.getNeighbors()){
            System.out.println("Option "+numberOfOptions+": Move to room "+currentRoom.getName());
        }
        for(Food currentFood : room.getFoodItems()){
            System.out.println("Option "+numberOfOptions+": Pick up food "+currentFood.toString());
        }
        for(Weapon currentWeapon : room.getWeapons()){
            System.out.println("Option "+numberOfOptions+": Pick up weapon "+currentWeapon.toString());
        }
    }

    //TODO
    private boolean validOption(String input){
        return true;
    }

    private List<Character> getLivingCharacters() {
        return maze.getLivingCharacters();
    }

    public void play() {
        while (!isOver()) {
            playTurn();
        }
        System.out.println("The game ended after "+turnCount+" turns.\n");
        String eventDescription;
        if (hasLivingPlayer()) {
            eventDescription = "You won! Congratulations, your money score is " + getPlayerMoney() + "\n";
        } else if (hasLivingMonsters()) {
            eventDescription = "You lost. The monsters won, your final money score is " + getPlayerMoney() + "\n";
        } else {
            eventDescription = "Both sides ended up dying. Unfortunately, you still lose, your final money score is " + getPlayerMoney() + "\n";
        }
        System.out.println(eventDescription);
    }

    private Double getPlayerMoney() {
        return getPlayer().getMoney();
    }

    private Character getPlayer() {
        return getLivingCharacters().stream()
                .filter(Character::isPlayer)
                .findFirst()
                .orElse(null);
    }

    public Character getWinner() {
        if (!isOver() || !hasLivingCharacters()) {
            // No one has won yet, or no one won -- all died
            return null;
        }
        return getLivingCharacters().getFirst();
    }

    private boolean hasLivingCharacters() {
        return maze.hasLivingCharacters();
    }
}
