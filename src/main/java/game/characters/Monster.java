package game.characters;

import game.Die;
import game.artifacts.Food;
import game.char_strategy.PlayStrategy;
import game.maze.Room;

public class Monster extends Character{
    final static Double DEFAULT_INITIAL_POINTS = 0.0;
    PlayStrategy strategy;
    public Monster(String name, Double health, PlayStrategy strategy, Double points, Die die) {
        super(name, health, points, die);
        this.strategy= strategy;
        this.points = DEFAULT_INITIAL_POINTS;
    }

    @Override
    public void doAction() {
        strategy.doAction(this, getCurrentLocation());
    }

    @Override
    public void move(Room destinationRoom) {
        assert getCurrentLocation().hasNeighbor(destinationRoom);
        Room previousLocation = getCurrentLocation();
        previousLocation.exited(this);
        destinationRoom.enter(this);
        System.out.println(getName()+" has moved");
    }

    @Override
    public void fight(Character foe){
        System.out.println(getName()+" saw you and will now engage in a fight with you!");
        Integer monsterRoll = getRoll();
        Integer playerRoll = foe.getRoll();
        if (monsterRoll > playerRoll) {
            Double healthLoss = (double) (monsterRoll - playerRoll);
            foe.loseHealth(healthLoss);
            foe.losePoints(DEFAULT_POINTS_LOST);
            System.out.println("Oh no, "+getName()+" won and you lost "+healthLoss+" and now has "+foe.getHealth()+". You also lost points and now have "+ foe.getPoints() +" points!");
        } else if (playerRoll > monsterRoll) {
            Double healthLoss = (double) (playerRoll - monsterRoll);
            loseHealth(healthLoss);
            System.out.println("Great job, you fought back against "+getName()+" and dealt "+healthLoss+" damage and now they have "+getHealth()+".");
            if(isDead()){
                foe.earnPoints(DEFAULT_POINTS_GAINED);
                System.out.println("Congratulations! You best the monster " +getName()+" and earned "+DEFAULT_POINTS_GAINED+", you now have "+ foe.getPoints() +" points!");
            }
        }
    }

    @Override
    public void eat(Food food) {
        this.gainHealth(food.healthValue());
        this.currentLocation.consumed(food);
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
