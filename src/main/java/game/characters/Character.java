package game.characters;

import game.Die;
import game.artifacts.Food;
import game.char_strategy.PlayStrategy;
import game.maze.Room;

public class Character {
    protected String name;
    Double health;
    Double money;
    Die die;
//    PlayStrategy strategy;

    private Room currentLocation;

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public Character(String name, Double health, Double money, Die die){
        this.name = name;
        this.health = health;
        this.money = money;
        this.die = die;
//        this.strategy = strategy;
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

    public Double getMoney(){
        return money;
    }

    void earnMoney(Double moneyAdded){
         money += moneyAdded;
    }

    void loseMoney(Double moneyLost){
        if (money <= 0){
            return;
        }
        money -= moneyLost;
    }

    void changeDie(Die newDie){
        die = newDie;
    }

    private int getRoll(){
        return die.roll();
    }

//    public void setStrategy(PlayStrategy newStrategy){
//        this.strategy = newStrategy;
//    }

    public Boolean isDead(){
        return health <= 0;
    }

    public Boolean isPlayer(){
        return true;
    }

    public Boolean isMonster(){
        return false;
    }

    public void enterRoom(Room room) {
        if (getCurrentLocation() != null) {
            if (getCurrentLocation().equals(room)) {
                return;
            }
            getCurrentLocation().remove(this);
        }
        this.currentLocation = room;
    }

    //These two are for polymorphism when calling Player's specific commands
    public void doAction(String input){}

    public void doAction(){}

    public void eat(Food food) {
        this.gainHealth(food.healthValue());
    }

    public void move(Room destinationRoom) {
        assert getCurrentLocation().hasNeighbor(destinationRoom);
        destinationRoom.enter(this);
    }



    public void fight(Character foe){
        Integer adventurerRoll = getRoll();
        Integer creatureRoll = foe.getRoll();
        if (adventurerRoll > creatureRoll) {
            Double healthLoss = (double) (adventurerRoll - creatureRoll);
            foe.loseHealth(healthLoss);
        } else if (creatureRoll > adventurerRoll) {
            Double healthLoss = (double) (creatureRoll - adventurerRoll);
            loseHealth(healthLoss);
        }
    }

    public void fightWBleed(Character foe, Double bleedHealth){
        Integer adventurerRoll = getRoll();
        Integer creatureRoll = foe.getRoll();
        if (adventurerRoll > creatureRoll) {
            Double healthLoss = (double) (adventurerRoll - creatureRoll);
            foe.loseHealth(healthLoss);
        } else if (creatureRoll > adventurerRoll) {
            Double healthLoss = (double) (creatureRoll - adventurerRoll);
            loseHealth(healthLoss);
        }
        foe.loseHealth(bleedHealth);
    }


}
