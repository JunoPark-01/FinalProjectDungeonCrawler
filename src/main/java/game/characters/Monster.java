package game.characters;

import game.Die;
import game.char_strategy.PlayStrategy;

public class Monster extends Character{
    public Monster(String name, Double health, Die die, PlayStrategy strategy) {
        super(name, health, die, strategy);
    }

    @Override
    public Boolean isPlayer(){
        return false;
    }

    @Override
    public Boolean isMonster(){
        return true;
    }

}
