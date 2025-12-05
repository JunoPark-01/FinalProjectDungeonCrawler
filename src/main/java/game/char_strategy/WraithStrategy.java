package game.char_strategy;

import game.artifacts.Food;
import game.maze.Room;
import game.characters.Character;
import game.maze.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WraithStrategy extends PlayStrategy {

    private final Random rand = new Random();
    private final Maze maze;

    public WraithStrategy(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void doAction(Character character, Room currentRoom) {
        if (currentRoom.hasLivingPlayer()) {
            character.fight(currentRoom.getLivingPlayer().getFirst());
            return;
        }

        List<Room> rooms = new ArrayList<>(maze.getRooms());
        rooms.remove(currentRoom);
        Room target = rooms.get(rand.nextInt(rooms.size()));
        character.move(target);
    }
}
