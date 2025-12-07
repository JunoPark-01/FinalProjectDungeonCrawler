package game.characters;

import game.Die;
import game.RandomDie;

import java.util.List;
import java.util.stream.IntStream;

public class CharacterFactory {
//    static final Double DEFAULT_PLAYER_INITIAL_HEALTH = 20.0;
    static final Double DEFAULT_MONSTER_INITIAL_HEALTH = 7.0;
    static final Double DEFAULT_STARTING_MONEY = 0.0;

    public Character createPlayer(String name){
        return Player.getInstance(name);
    }

    public Character createPlayer(String name, Die die){
        return Player.getInstance(name, die);
    }

    public Character createMonster(String name){
        Die die = RandomDie.sixSided();
        return new Monster(name, DEFAULT_MONSTER_INITIAL_HEALTH, DEFAULT_STARTING_MONEY, die);
    }

    public List<Character> createMonsters(int numberOfMonsters){
        return IntStream.range(0, numberOfMonsters)
                .mapToObj(i -> createMonster("Monster "+ (i+1)))
                .toList();
    }
}
