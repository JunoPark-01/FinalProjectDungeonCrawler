package game.characters;

import game.Die;
import game.RandomDie;
import game.RiggedDie;
import game.artifacts.Food;
import game.char_strategy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CharacterFactory {
//    static final Double DEFAULT_PLAYER_INITIAL_HEALTH = 20.0;
    static final Double DEFAULT_MONSTER_INITIAL_HEALTH = 7.0;
    static final Double DEFAULT_DEMON_INITIAL_HEALTH = 10.0;
    static final Double DEFAULT_LEECH_INITIAL_HEALTH = 5.0;
    static final Double DEFAULT_DARKGOD_INITIAL_HEALTH = 15.0;
    static final Double DEFAULT_STARTING_MONEY = 0.0;

    public Character createPlayer(String name){
        return Player.getInstance(name);
    }

    public Character createPlayer(String name, Die die){
        return Player.getInstance(name, die);
    }

    public Monster createDemon(String name) {
        return createDemon(name, DEFAULT_DEMON_INITIAL_HEALTH);
    }

    public Monster createDemon(String name, Double initialHealth) {
        return new Monster(name, initialHealth, new DemonStrategy(), DEFAULT_STARTING_MONEY, RandomDie.sixSided());
    }

    public Monster createLeech(String name) {
        return createLeech(name, DEFAULT_LEECH_INITIAL_HEALTH);
    }

    public Monster createLeech(String name, Double initialHealth) {
        return new Monster(name, initialHealth, new LeechStrategy(), DEFAULT_STARTING_MONEY, RandomDie.sixSided());
    }

    public Monster createDarkGlutton(String name) {
        return createDarkGlutton(name, DEFAULT_LEECH_INITIAL_HEALTH);
    }

    public Monster createDarkGlutton(String name, Double initialHealth) {
        return new Monster(name, initialHealth, new DarkGluttonStrategy(), DEFAULT_STARTING_MONEY, RandomDie.sixSided());
    }

    public Monster createDarkGod(String name) {
        return createDarkGod(name, DEFAULT_DARKGOD_INITIAL_HEALTH);
    }

    public Monster createDarkGod(String name, Double initialHealth) {
        return new Monster(name, initialHealth, new DarkGodStrategy(), DEFAULT_STARTING_MONEY, new RiggedDie(10));
    }

    public Character createGlutton(String name, Die die){
        return Player.Glutton.getGluttonInstance(name, die);
    }

    public Monster createMonster(String name){
        Die die = RandomDie.sixSided();
        return new Monster(name, DEFAULT_MONSTER_INITIAL_HEALTH, new MonsterStrategy(), DEFAULT_STARTING_MONEY, die);
    }

    public List<Monster> createMonsters(int numberOfMonsters){
        return IntStream.range(0, numberOfMonsters)
                .mapToObj(i -> createMonster("Monster "+ (i+1)))
                .toList();
    }

    public List<Monster> createMonstersAndMonsterStrategies(int numberOfMonsters){
        List<Monster> monsterList = new ArrayList<>();
        IntStream.range(0, numberOfMonsters)
                .mapToObj(i -> createMonster("Monster "+ (i+1)))
                .forEach(monsterList::add);
        monsterList.add(createDemon("Demon"));
        monsterList.add(createLeech("Leech"));
        monsterList.add(createDarkGlutton("Glutton"));
        monsterList.add(createDarkGod("Dark God"));

        return monsterList;

    }
}
