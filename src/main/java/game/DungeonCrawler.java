package game;

import game.artifacts.Food;
import game.artifacts.Weapon;
import game.characters.Character;
import game.maze.*;
import game.characters.*;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
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
                    List<String> listOfOptions = createListOfOptions(character.getCurrentLocation());
                    displayStats(character);
                    displayOptions(listOfOptions);
                    System.out.println("Choose an option number");
                    String input = scanner.nextLine();
                    while (!validOption(input, listOfOptions)) {
                        System.out.println("Please pick a valid option");
                        input = scanner.nextLine();
                    }
                    System.out.println();
                    System.out.println("You decided to go with option "+input);
                    character.doAction(input, listOfOptions);
                }
            }else{  //monster
                if(!character.isDead()){
                    //TODO What should the monsters do
                    character.doAction();
//                    System.out.println("Monster "+character.getName()+" moved");

                }
            }
            characters.remove(index);
        }
    }

    private void displayStats(Character player) {
        System.out.println("Player name :"+player.getName());
        System.out.println("Health: "+ player.getHealth());
        System.out.println("Current Room: "+player.getCurrentLocation().getName());
        if(player.getWeapon() == null){
            System.out.println("No weapon currently equipped");
        }else{
            System.out.println("Equipped Weapon: "+ player.getWeapon().toString());
        }
        System.out.println();
    }

    private List<String> createListOfOptions(Room room){
        List<String> result = new ArrayList<>();
        int numberOfOptions = 1;
        for(Room currentRoom : room.getNeighbors()){
            result.add("Option "+numberOfOptions+": Move to room "+currentRoom.getName());
            numberOfOptions ++;
        }
        for(Food currentFood : room.getFoodItems()){
            result.add("Option "+numberOfOptions+": Pick up food "+currentFood.toString());
            numberOfOptions ++;
        }
        for(Weapon currentWeapon : room.getWeapons()){
            result.add("Option "+numberOfOptions+": Pick up weapon "+currentWeapon.toString());
            numberOfOptions ++;
        }
        for(Character currentMonster : room.getLivingMonsters()){
            result.add("Option "+numberOfOptions+": Fight the monster "+currentMonster.getName());
            numberOfOptions ++;
        }
        System.out.println("\n");
        return result;
    }

    private void displayOptions(List<String> listOfOptions){
        for(String option : listOfOptions){System.out.println(option);}
    }

    //Used ChatGPT to figure out how to check the parseInt would be seen as an integer
    //so we won't run into an error on checking its value also being in between
    //the possible options.
    private boolean actuallyNumberedString(String input){
        try {
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException stringNotJustIntegerError){
            return false;
        }
    }

    private boolean validOption(String input, List<String> listOfOptions){
        if(actuallyNumberedString(input)){return Integer.parseInt(input) > 0 && Integer.parseInt(input) <= listOfOptions.size();}
        return false;
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
