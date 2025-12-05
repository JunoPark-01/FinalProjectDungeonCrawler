package game.characters;

import game.Die;
import game.char_strategy.PlayStrategy;

public class Monster extends Character{
    public Monster(String name, Double health, Double money, Die die) {
        super(name, health, money, die);
        this.money = 0.0;
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
