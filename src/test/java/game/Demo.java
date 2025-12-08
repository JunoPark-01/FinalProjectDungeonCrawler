package game;

import game.artifacts.FoodFactory;
import game.artifacts.WeaponFactory;
import game.characters.CharacterFactory;
import game.chest.ChestFactory;
import game.maze.Maze;
import game.maze.RoomFactory;

import java.util.Scanner;

public class Demo {
    static void introduction(){
        System.out.println("Welcome to the dungeon crawler adventure! The main objective is to survive of course, but you will be given a score based on the amount of money you've accumulated on your journey!");
        System.out.println("Here, you'll be able to pick up items that will assist you in combat, so do your best to survive as long as you can!");
        System.out.println("What's your name traveler?");
    }

    public static void main(String[] args) {
        RoomFactory myRoomFactory = new RoomFactory();
        CharacterFactory myCharacterFactory = new CharacterFactory();
        ChestFactory myChestFactory = new ChestFactory();
        Scanner myScanner = new Scanner(System.in);
        Maze myMaze;
        introduction();
        String playerName = myScanner.nextLine();
        System.out.println("Welcome "+ playerName+", we will begin creating your dungeon crawler experience!");
        WeaponFactory myWeaponFactory = new WeaponFactory();
        FoodFactory myFoodFactory = new FoodFactory();
        myMaze = Maze.getNewBuilder(myRoomFactory)
                .createNRoomsWithMConnections(4, 4)
                .distributeRandomly()
                .addPlayer(myCharacterFactory.createPlayer(playerName))
                .addMonsters(myCharacterFactory.createMonsters(2))
                .addWeapon(myWeaponFactory.createWeaponTypes(3,3))
                .addFood(myFoodFactory.createFoodTypes(2,2,2))
                .addChests(myChestFactory, 4)
                .build();
        DungeonCrawler newDungeonCrawlerGame = new DungeonCrawler(myMaze);
        newDungeonCrawlerGame.play();
    }
}
