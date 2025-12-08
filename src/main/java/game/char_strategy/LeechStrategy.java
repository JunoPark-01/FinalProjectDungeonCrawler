package game.char_strategy;

import game.characters.Character;
import game.maze.Room;

public class LeechStrategy extends PlayStrategy{
    final static Double BLEED_DAMAGE = 1.0;
    @Override
    public void doAction(Character character, Room currentRoom) {
        if (currentRoom.hasLivingPlayer()) {
            Character foe = currentRoom.getLivingPlayer().getFirst();
            character.fightWBleed(foe, BLEED_DAMAGE);
            return;
        }
        character.move(currentRoom.getRandomNeighbor());
    }
}
