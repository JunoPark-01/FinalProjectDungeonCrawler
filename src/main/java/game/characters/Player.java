package game.characters;

import game.Die;
import game.RandomDie;
import game.artifacts.Food;
import game.artifacts.Key;
import game.artifacts.Weapon;
import game.char_strategy.PlayStrategy;
import game.chest.KeyChest;
import game.chest.KeyChestAdapter;
import game.chest.WeaponChest;
import game.chest.WeaponChestAdapter;
import game.maze.Room;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game.characters.Player.Glutton.DEFAULT_GLUTTON_INITIAL_HEALTH;

public class Player extends Character{
    private static Player currentPlayer;
    final static Double DEFAULT_INITIAL_HEALTH = 20.0;
    final static Double DEFAULT_INITIAL_MONEY = 0.0;
    final static Double DEFAULT_POINTS_GAINED = 10.0;
    final static Double DEFAULT_POINTS_LOST = 1.0;
    final static Die DEFAULT_INITIAL_DIE = RandomDie.sixSided();
    final static List<String> POSSIBLE_ACTIONS = List.of("Move", "Pick up food", "Pick up weapon",
            "Fight", "Open locked room", "Open a chest");
    final static Random randomness = new Random();

    private Player(String name, Double health, Double points, Die die){
        super(name, health, points, die);
        this.points = points;
    }

    public static Player getInstance(String name, Die die){
        if(currentPlayer == null){currentPlayer = new Player(name, DEFAULT_INITIAL_HEALTH, DEFAULT_INITIAL_MONEY, die);}
        return currentPlayer;
    }

    public static Player getInstance(String name){
        if(currentPlayer == null){currentPlayer = new Player(name, DEFAULT_INITIAL_HEALTH, DEFAULT_INITIAL_MONEY, DEFAULT_INITIAL_DIE);}
        return currentPlayer;
    }

    @Override
    public void doAction(String input, List<String> listOfOptions) {
        String actionTaken = listOfOptions.get(Integer.parseInt(input) - 1);

        //Move option
        if (actionTaken.contains(POSSIBLE_ACTIONS.get(0))) {
            movementActionSelected(actionTaken);

            //Pick up food option
        } else if (actionTaken.contains(POSSIBLE_ACTIONS.get(1))) {
            pickUpFoodActionSelected(actionTaken);

            //Pick up weapon option
        } else if (actionTaken.contains(POSSIBLE_ACTIONS.get(2))) {
            pickUpWeaponActionSelected(actionTaken);

            //Fight option
        } else if (actionTaken.contains(POSSIBLE_ACTIONS.get(3))) {
            fightActionSelected(actionTaken);

        } else if (actionTaken.contains(POSSIBLE_ACTIONS.get(4))) {
            unlockDoorSelected();
        }else if(actionTaken.contains(POSSIBLE_ACTIONS.get(5))){
            openRandomChestFromSelection();

        }else{
            System.out.println("This option is not valid to do any action upon");
        }
    }

    private void openRandomChestFromSelection() {
        int sizeOfChestsOpenable = getCurrentLocation().getWeaponChests().size()-1;
        if(getCurrentLocation().getWeaponChests().isEmpty()){sizeOfChestsOpenable=0;}
        if(getCurrentLocation().getKeyChest() != null){
            openRandomChestFromSelectionWithKeyChest(sizeOfChestsOpenable+1);
        }else{
            int choice;
            if(sizeOfChestsOpenable == 0){
                choice = 0;
            }else {
                choice = randomness.nextInt(sizeOfChestsOpenable);
            }
            openRandomChestFromSelectionWithoutKeyChest(choice);
        }

    }

    private void openRandomChestFromSelectionWithoutKeyChest(int choice) {
        WeaponChest currentChest = getCurrentLocation().getWeaponChests().get(choice);
        Weapon newItem = (Weapon)new WeaponChestAdapter(currentChest).open();
        equipFromChest(newItem, currentChest);
        System.out.println("Would you look at that, you found a "+newItem.toString()+" from the chest! Fortunately, or unfortunately, this will be your new weapon! Good luck!");
    }

    private void equipFromChest(Weapon newItem, WeaponChest currentChest) {
        getCurrentLocation().getWeaponChests().remove(currentChest);
        weapon = newItem;
    }

    private void openRandomChestFromSelectionWithKeyChest(int newSize){
        int choice = randomness.nextInt(newSize);
        if(choice == newSize || getCurrentLocation().getWeaponChests().isEmpty()){
            KeyChest currentChest = getCurrentLocation().getKeyChest();
            Key newItem = (Key)new KeyChestAdapter(currentChest).open();
            pickupKey(newItem);
            System.out.println("Wow, you found the key "+newItem.getName()+" from the chest! If you find the locked door, you can leave and finish the game!");
        }else{
            openRandomChestFromSelectionWithoutKeyChest(choice);
        }
    }

    private void unlockDoorSelected() {
        if(hasKey()){
            System.out.println("You have the key that unlocks this door! You have now entered and ended the game");
            enteredThroughLockedDoor = true;
        }else{
            System.out.println("You unfortunately do not have the key to unlock this door. Please look for it before entering");
        }
    }

    private void pickUpWeaponActionSelected(String inputAction) {
        for(Weapon currentWeapon: this.getCurrentLocation().getWeapons()){
            if(inputAction.contains(currentWeapon.toString())){
                this.equip(currentWeapon);
                return;
            }
        }
        System.out.println("Well, we couldn't find the food you were looking for.");
    }

    private void pickUpFoodActionSelected(String inputAction) {
        for(Food currentFood : this.getCurrentLocation().getFoodItems()){
            if(inputAction.contains(currentFood.toString())){
                this.eat(currentFood);
                return;
            }
        }
        System.out.println("Well, we couldn't find the food you were looking for.");
    }

    private void fightActionSelected(String inputAction) {
        for(Character currentMonster : this.getCurrentLocation().getLivingMonsters()){
            if(inputAction.contains(currentMonster.getName())){
                this.fight(currentMonster);
                return;
            }
        }
        System.out.println("Well, we couldn't find the monster you were looking for.");
    }

    private void movementActionSelected(String actionInput) {
        for(Room currentRoom : this.getCurrentLocation().getNeighbors()){
            if(actionInput.contains(currentRoom.getName())){
                this.enterRoom(currentRoom);
                return;
            }
        }
        System.out.println("Well, we couldn't find the room you were looking for.");
    }

    @Override
    public void fight(Character foe){
        System.out.println(getName()+" is now fighting " +foe.getName());
        Integer playerRoll = getRoll();
        Integer monsterRoll = foe.getRoll();
        if (playerRoll > monsterRoll) {
            Double healthLoss = (double) (playerRoll - monsterRoll);
            foe.loseHealth(healthLoss);
            System.out.println("You won the fight! "+foe.getName()+" lost "+healthLoss+" and now has "+foe.getHealth()+".");
            if(foe.getHealth() <= 0){
                earnPoints(DEFAULT_POINTS_GAINED);
                System.out.println("Congratulations! You best the monster " +foe.getName()+" and earned "+DEFAULT_POINTS_GAINED+", you now have "+ getPoints() +" points!");
            }
        } else if (monsterRoll > playerRoll) {
            Double healthLoss = (double) (monsterRoll - playerRoll);
            loseHealth(healthLoss);
            losePoints(DEFAULT_POINTS_LOST);
            System.out.println("You lost the fight! "+foe.getName()+" dealt "+healthLoss+" damage and now you have "+getHealth()+". You have also lost "+ DEFAULT_POINTS_LOST +" for losing and now have "+ getPoints() +" points.");
        }
    }

    public void pickupKey(Key newKey){
        hasKey = newKey;
        getCurrentLocation().keyChestOpened();
    }

    public static class Glutton extends Player {
        final static Double DEFAULT_GLUTTON_INITIAL_HEALTH = 15.0;
        private Glutton(String name, Die die) {
            super(name, DEFAULT_GLUTTON_INITIAL_HEALTH, DEFAULT_INITIAL_MONEY, die);
        }

        @Override
        public void eat(Food food) {
            this.gainHealth(food.healthValue()*2);
            System.out.println("You've eaten the food "+food.toString());
            this.getCurrentLocation().consumed(food);
        }
    }

    public static Player getGluttonInstance(String name, Die die){
        if(currentPlayer == null){currentPlayer = new Glutton(name, die);}
        return currentPlayer;
    }

}
