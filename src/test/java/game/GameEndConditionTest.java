package game;

import game.artifacts.FoodFactory;
import game.artifacts.WeaponFactory;
import game.characters.CharacterFactory;
import game.maze.Maze;
import game.maze.RoomFactory;

import java.util.Scanner;

public class GameEndConditionTest {
    //TODO The way to win should be through the key, for now the way to win is being the only one left alive
    static void introduction(){
        System.out.println("Welcome to the dungeon crawler adventure! The main objective is to survive of course, but you will be given a score based on the amount of money you've accumulated on your journey!");
        System.out.println("Here, you'll be able to pick up items that will assist you in combat, so do your best to survive as long as you can!");
        System.out.println("What's your name traveler?");
    }

    public static void main(String[] args) {
        RoomFactory myRoomFactory = new RoomFactory();
        WeaponFactory myWeaponFactory = new WeaponFactory();
        FoodFactory myFoodFactory = new FoodFactory();
        CharacterFactory myCharacterFactory = new CharacterFactory();
        Die riggedDie = new RiggedDie(3);
        Scanner myScanner = new Scanner(System.in);
        Maze myMaze;
        introduction();
        String playerName = myScanner.nextLine();
        System.out.println("Welcome "+ playerName+", we will begin creating your dungeon crawler experience!");
        myMaze = Maze.getNewBuilder(myRoomFactory)
                .createNRoomsWithMConnections(4, 4)
                .distributeRandomly()
                .addPlayer(myCharacterFactory.createPlayer(playerName))
                .addMonsters(myCharacterFactory.createMonstersAndMonsterStrategies(1))
                .addWeapon(myWeaponFactory.createWeaponTypes(1,1))
                .addFood(myFoodFactory.createFoodTypes(1,1,1))
                .build();
        DungeonCrawler newDungeonCrawlerGame = new DungeonCrawler(myMaze);
        newDungeonCrawlerGame.play();
    }
}
