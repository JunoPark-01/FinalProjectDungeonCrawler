package game.char_strategy;

import game.RiggedDie;
import game.characters.Character;
import game.maze.Room;

public class DarkGodStrategy extends PlayStrategy{


    @Override
    public void doAction(Character character, Room currentRoom) {
//        character.setDie(new RiggedDie(10));
        if (currentRoom.hasLivingPlayer()) {
            character.fight(currentRoom.getLivingPlayer().getFirst());
        }
    }


}
