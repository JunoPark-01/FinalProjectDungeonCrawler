package game.char_strategy;

import game.artifacts.Food;
import game.maze.Room;
import game.characters.Character;

public class DemonStrategy extends PlayStrategy {
    @Override
    public void doAction(Character character, Room currentRoom) {
        if (currentRoom.hasLivingPlayer()) {
            character.fight(currentRoom.getLivingPlayer().getFirst());
            return;
        }
        character.move(currentRoom.getRandomNeighbor());
    }
}
