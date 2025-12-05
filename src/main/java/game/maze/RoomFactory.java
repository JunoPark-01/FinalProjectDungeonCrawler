package game.maze;

public class RoomFactory {

    public static String[] NAMES = new String[]{"The Bog", "Cavernous Wasteland", "District 13", "Village of Wrath",
            "Icey Peaks", "Lost Dragon's Den", "Fairy Kingdom", "Rolling Hills", "Forest of Agony", "Infected Lands",
            "Ashes of the Firelands", "The Petulance Bunker", "Castle", "Basement of Fear", "Sloth Swamp", "No Mans Land"};

    private int currentNameIndex = 0;

    Room createRoom(String name) {
        return new Room(name);
    }

    Room createRoom() {
        return createRoom(getNextRoomName());
    }

    private String getNextRoomName() {
        String candidateName = NAMES[currentNameIndex++ % NAMES.length];
        if (currentNameIndex >= NAMES.length) {
            candidateName += (currentNameIndex / NAMES.length);
        }
        return candidateName;
    }

}
