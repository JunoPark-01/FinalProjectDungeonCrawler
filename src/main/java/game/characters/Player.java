package game.characters;

import game.Die;
import game.RandomDie;
import game.char_strategy.PlayStrategy;

public class Player extends Character{
    private static Player currentPlayer;
    final static Double DEFAULT_INITIAL_HEALTH = 10.0;
    final static Double DEFAULT_INITIAL_MONEY = 0.0;
    final static Die DEFAULT_INITIAL_DIE = RandomDie.sixSided();


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
    public void doAction(String input){

    }

}
