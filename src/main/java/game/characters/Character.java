package game.characters;

import game.Die;
import game.char_strategy.PlayStrategy;
import game.maze.Room;

public class Character {
    protected String name;
    Double health;
    Die die;
    PlayStrategy strategy;

    private Room currentLocation;



    public Room getCurrentLocation() {
        return currentLocation;
    }

    public Character(String name, Double health, Die die, PlayStrategy strategy){
        this.name = name;
        this.health = health;
        this.die = die;
        this.strategy = strategy;
    }

    public String getName(){
        return name;
    }

    public Double getHealth(){
        return health;
    }

    void gainHealth(Double healthGain){
        health += healthGain;
    }

    void loseHealth(Double healthLoss){
        if (health <= 0){
            return;
        }
        health -= healthLoss;
    }

    void changeDie(Die newDie){
        die = newDie;
    }

    private int getRoll(){
        return die.roll();
    }

    public void setStrategy(PlayStrategy newStrategy){
        this.strategy = newStrategy;
    }

    public Boolean isDead(){
        return health <= 0;
    }

    public Boolean isPlayer(){
        return true;
    }

    public Boolean isMonster(){
        return false;
    }

    //TODO
    public void enterRoom(Room room) {

    }

/*
    public void fight(){

    }
    public void move(){

    }
*/

}
