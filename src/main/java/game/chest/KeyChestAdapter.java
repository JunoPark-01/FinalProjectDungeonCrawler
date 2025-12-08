package game.chest;

public class KeyChestAdapter implements Chest{
    private final KeyChest keyChest = new KeyChest();


    @Override
    public Object open(){
        return keyChest.createKey();
    }
}
