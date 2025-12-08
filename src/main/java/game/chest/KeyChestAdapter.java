package game.chest;

public class KeyChestAdapter implements Chest{
    private final KeyChest keyChest;

    public KeyChestAdapter(KeyChest chest){this.keyChest = chest;}

    @Override
    public Object open(){
        return keyChest.createKey();
    }
}
