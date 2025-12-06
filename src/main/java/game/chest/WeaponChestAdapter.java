package game.chest;

import game.chest.WeaponChest;

public class WeaponChestAdapter implements Chest{
    private final WeaponChest weaponChest;

    public WeaponChestAdapter(WeaponChest chest){
        this.weaponChest = chest;
    }

    @Override
    public Object open(){
        return weaponChest.createWeapon();
    }
}
