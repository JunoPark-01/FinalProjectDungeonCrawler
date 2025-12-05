package game.characters;

import game.Die;
import game.RandomDie;

public class CharacterFactory {
    static final Double DEFAULT_PLAYER_INITIAL_HEALTH = 20.0;
    static final Double DEFAULT_MONSTER_INITIAL_HEALTH = 15.0;
    static final Double DEFAULT_STARTING_MONEY = 0.0;

    public Character createPlayer(String name){
        Die die = RandomDie.sixSided();
        return new Character(name, DEFAULT_PLAYER_INITIAL_HEALTH, DEFAULT_STARTING_MONEY, die);
    }

    public Character createMonster(String name){
        Die die = RandomDie.sixSided();
        return new Monster(name, DEFAULT_MONSTER_INITIAL_HEALTH, DEFAULT_STARTING_MONEY, die);
    }
}
