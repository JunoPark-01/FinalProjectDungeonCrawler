package game.char_strategy;

import game.maze.Room;
import game.characters.Character;
import game.maze.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DarkGluttonStrategy extends PlayStrategy {



    @Override
    public void doAction(Character character, Room currentRoom) {
        if (currentRoom.hasFood()) {
            character.eat(currentRoom.getFood().get());
            return;
        }

        character.move(currentRoom.getRandomNeighbor());
    }
}
