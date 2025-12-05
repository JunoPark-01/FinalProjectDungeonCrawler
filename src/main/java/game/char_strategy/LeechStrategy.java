package game.char_strategy;

import game.characters.Character;
import game.maze.Room;

public class LeechStrategy extends PlayStrategy{
    @Override
    public void doAction(Character character, Room currentRoom) {
        if (currentRoom.hasLivingPlayer()) {
            Character foe = currentRoom.getLivingPlayer().getFirst();
            character.fightWBleed(foe, 1.0);
            return;
        }
        character.move(currentRoom.getRandomNeighbor());
    }
}
