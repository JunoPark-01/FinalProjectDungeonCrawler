package game.artifacts;

import game.Die;
import game.RandomDie;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class WeaponFactory {
    private static final Random random = new Random();


    private static final String[] BASIC_WEAPON_NAMES = new String[]{
            "dagger", "shiv", "slingshot", "mallet", "hoe", "bat", "stick", "brass knuckles"};

    private static final String[] GOOD_WEAPON_NAMES = new String[]{
            "sword", "axe", "hatchet", "spear", "mace", "shortsword", "pike", "bow"};

    private static final String[] GREAT_WEAPON_NAMES = new String[]{
            "longsword", "warhammer", "gun", "silver dagger", "trident", "morningstar", "lance", "flail"};

    private static String getRandomBasicWeaponName() {
        return BASIC_WEAPON_NAMES[random.nextInt(BASIC_WEAPON_NAMES.length)];
    }

    private static String getRandomGoodWeaponName() {
        return GOOD_WEAPON_NAMES[random.nextInt(GOOD_WEAPON_NAMES.length)];
    }

    private static String getRandomGreatWeaponName() {
        return GREAT_WEAPON_NAMES[random.nextInt(GREAT_WEAPON_NAMES.length)];
    }

    public Weapon createWeapon(String name, Die die) {
        return new Weapon(name, die);
    }

    public List<Weapon> createWeapons(int numberOfWeapons){
        return IntStream.range(0, numberOfWeapons)
                .mapToObj(_ -> createBasicWeapon())
                .toList();
    }

    public Weapon createBasicWeapon() {
        Die die = RandomDie.eightSided();
        return createWeapon(getRandomBasicWeaponName(), die);
    }

    public Weapon createGoodWeapon() {
        Die die = RandomDie.twelveSided();
        return createWeapon(getRandomGoodWeaponName(), die);
    }

    public Weapon createGreatWeapon() {
        Die die = RandomDie.twentySided();
        return createWeapon(getRandomGreatWeaponName(), die);
    }
}
