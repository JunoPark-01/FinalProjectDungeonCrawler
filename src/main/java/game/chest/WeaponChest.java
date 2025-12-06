package game.chest;

import game.RandomDie;
import game.artifacts.Weapon;
import game.artifacts.WeaponFactory;

import java.util.Random;

public class WeaponChest {
    private static final int MINIMUM_VALUE_FOR_RANDOM_INT_RANGE = 1;
    private static final int NUM_WEAPON_TYPES = 3;

    private final WeaponFactory factory;



    public WeaponChest(WeaponFactory factory) {
        this.factory = factory;
    }

    public  Weapon createWeapon() {
        int randomWeaponType = MINIMUM_VALUE_FOR_RANDOM_INT_RANGE + new Random().nextInt(NUM_WEAPON_TYPES);
        if (randomWeaponType == MINIMUM_VALUE_FOR_RANDOM_INT_RANGE){
            return factory.createBasicWeapon();
        }
        else if (randomWeaponType == NUM_WEAPON_TYPES){
            return factory.createGreatWeapon();
        }
        return factory.createGoodWeapon();
    }

}
