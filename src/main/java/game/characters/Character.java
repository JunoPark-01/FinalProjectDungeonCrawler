package game.characters;

import game.Die;
import game.artifacts.Food;
import game.artifacts.Key;
import game.artifacts.Weapon;
import game.char_strategy.PlayStrategy;
import game.maze.Room;

import java.util.List;

public class Character {
    final static Double DEFAULT_POINTS_GAINED = 10.0;
    final static Double DEFAULT_POINTS_LOST = 1.0;
    protected String name;
    Double health;
    Double points;
    Die die;
    Weapon weapon;
    Key hasKey = null;
    Boolean enteredThroughLockedDoor = false;
//    PlayStrategy strategy;

    protected Room currentLocation;

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public Character(String name, Double health, Double points, Die die){
        this.name = name;
        this.health = health;
        this.points = points;
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

    public Double getPoints(){
        return points;
    }

    void earnPoints(Double pointsAdded){
         points += pointsAdded;
    }

    void losePoints(Double pointsLost){
        points -= pointsLost;
        if (points <= 0){
            points = 0.0;
            return;
        }
    }

//    public void setDie(Die newDie){
//        this.die = newDie;
//    }

    int getRoll(){
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
        }
        this.currentLocation = room;
    }

    //This is for polymorphism when calling Player's specific commands
    public void doAction(String input, List<String> listOfOptions) {}

    public void doAction(){}

    public void eat(Food food) {
        this.gainHealth(food.healthValue());
        System.out.println("You've eaten the food "+food.toString());
        this.currentLocation.consumed(food);
    }

    public void move(Room destinationRoom) {
        assert getCurrentLocation().hasNeighbor(destinationRoom);
        System.out.println("You've moved from "+getCurrentLocation().getName()+" to  "+destinationRoom.getName());
        this.currentLocation = destinationRoom;
        destinationRoom.enter(this);
    }



    public void fight(Character foe){
        System.out.println(getName()+" is now fighting " +foe.getName());
        Integer playerRoll = getRoll();
        Integer monsterRoll = foe.getRoll();
        if (playerRoll > monsterRoll) {
            Double healthLoss = (double) (playerRoll - monsterRoll);
            foe.loseHealth(healthLoss);
            System.out.println("You won the fight! "+foe.getName()+" lost "+healthLoss+" and now has "+foe.getHealth()+".");
        } else if (monsterRoll > playerRoll) {
            Double healthLoss = (double) (monsterRoll - playerRoll);
            loseHealth(healthLoss);
            System.out.println("You lost the fight! "+foe.getName()+" dealt "+healthLoss+" damage and now you have "+getHealth()+".");
        }
    }

    public void fightWBleed(Character foe, Double bleedHealth){
        Integer playerRoll = getRoll();
        Integer monsterRoll = foe.getRoll();
        if (playerRoll > monsterRoll) {
            Double healthLoss = (double) (playerRoll - monsterRoll);
            foe.loseHealth(healthLoss);
        } else if (monsterRoll > playerRoll) {
            Double healthLoss = (double) (monsterRoll - playerRoll);
            loseHealth(healthLoss);
        }
        foe.loseHealth(bleedHealth);
    }

    protected void equip(Weapon currentWeapon) {
        weapon = currentWeapon;
        currentLocation.getWeapons().remove(currentWeapon);
        System.out.println("Congratulations! You've equipped the weapon "+weapon.toString()+".");
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public boolean hasKey(){
        return hasKey != null;
    }

    public Key getKey(){
        return hasKey;
    }

    public Boolean getEnteredThroughLockedDoor(){ return enteredThroughLockedDoor; }
}
