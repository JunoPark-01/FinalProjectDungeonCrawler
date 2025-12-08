package game.chest;

import game.artifacts.WeaponFactory;

import java.util.List;
import java.util.stream.IntStream;

public class ChestFactory {
    private final WeaponFactory myWeaponFactory = new WeaponFactory();

    public KeyChest createKeyChest(){
        return new KeyChest();
    }

    private WeaponChest createWeaponChest(){
        return new WeaponChest(myWeaponFactory);
    }

    public List<WeaponChest> createWeaponChests(int numberOfWeaponChests){
        return IntStream.range(0, numberOfWeaponChests)
                .mapToObj(_ -> createWeaponChest()).toList();
    }
}
