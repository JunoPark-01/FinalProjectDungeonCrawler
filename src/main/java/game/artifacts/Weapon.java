package game.artifacts;

import game.Die;

public class Weapon {
    private final String name;
    private final Die die;

    public Weapon(String name, Die die){
        this.name = name;
        this.die = die;
    }

    public Die getDie(){
        return die;
    }

    @Override
    public String toString() {
        return name + "(" + die.toString() + ")";
    }
}
