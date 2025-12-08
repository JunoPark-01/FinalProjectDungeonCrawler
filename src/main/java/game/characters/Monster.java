package game.characters;

import game.Die;
import game.char_strategy.PlayStrategy;
import game.maze.Room;

public class Monster extends Character{
    PlayStrategy strategy;
    public Monster(String name, Double health, PlayStrategy strategy, Double points, Die die) {
        super(name, health, points, die);
        this.strategy= strategy;
        this.points = 0.0;
    }

    @Override
    public void doAction() {
        strategy.doAction(this, getCurrentLocation());
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
