package game.char_strategy;

import game.artifacts.Food;
import game.maze.Room;
import game.characters.Character;

import java.util.Optional;

abstract public class PlayStrategy {

    static void eatFood(Character myself, Room currentRoom) {
        Optional<Food> food = currentRoom.getFood();
        if (food.isPresent()) {
            myself.eat(food.get());
        } else {
            System.out.printf("There is no food in the room");
        }
    }

    abstract public void doAction(Character character, Room currentRoom);

    boolean shouldMove(Room room) {
        return true;
    }
}
