package game.characters;

import game.Die;
import game.RandomDie;
import game.artifacts.Food;
import game.artifacts.Weapon;
import game.char_strategy.PlayStrategy;
import game.maze.Room;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character{
    private static Player currentPlayer;
    final static Double DEFAULT_INITIAL_HEALTH = 20.0;
    final static Double DEFAULT_INITIAL_MONEY = 0.0;
    final static Die DEFAULT_INITIAL_DIE = RandomDie.sixSided();
    final static List<String> POSSIBLE_ACTIONS = List.of("Move", "Pick up food", "Pick up weapon", "Fight");

    private Player(String name, Double health, Double money, Die die){
        super(name, health, money, die);
        this.money = money;
    }

    public static Player getInstance(String name, Double health, Double money, Die die){
        if(currentPlayer == null){currentPlayer = new Player(name, health, money, die);}
        return currentPlayer;
    }

    public static Player getInstance(String name){
        if(currentPlayer == null){currentPlayer = new Player(name, DEFAULT_INITIAL_HEALTH, DEFAULT_INITIAL_MONEY, DEFAULT_INITIAL_DIE);}
        return currentPlayer;
    }

    @Override
    public void doAction(String input, List<String> listOfOptions){
        String actionTaken = listOfOptions.get(Integer.parseInt(input)-1);

        //Move option
        if(actionTaken.contains(POSSIBLE_ACTIONS.get(0))){
            movementActionSelected(actionTaken);

        //Pick up food option
        }else if(actionTaken.contains(POSSIBLE_ACTIONS.get(1))){
            pickUpFoodActionSelected(actionTaken);

        //Pick up weapon option
        }else if(actionTaken.contains(POSSIBLE_ACTIONS.get(2))){
            pickUpWeaponActionSelected(actionTaken);

        //Fight option
        }else if(actionTaken.contains(POSSIBLE_ACTIONS.get(3))){
            fightActionSelected(actionTaken);

        }else{
            System.out.println("This option is not valid to do any action upon");
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

}
