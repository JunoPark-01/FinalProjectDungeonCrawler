package game.chest;

import game.RandomDie;
import game.artifacts.Weapon;
import game.artifacts.WeaponFactory;

import java.util.Random;

public class WeaponChest {

    private final WeaponFactory factory;

    public WeaponChest(WeaponFactory factory) {
        this.factory = factory;
    }

    public Weapon createWeapon() {
        return factory.createBasicWeapon();
    }

}
