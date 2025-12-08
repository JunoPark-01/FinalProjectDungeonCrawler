package game.characters;

import game.Die;
import game.char_strategy.PlayStrategy;

public class Monster extends Character{
    public Monster(String name, Double health, Double points, Die die) {
        super(name, health, points, die);
        this.points = 0.0;
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
