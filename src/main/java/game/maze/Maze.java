package game.maze;

import java.util.*;
import java.util.stream.IntStream;
import game.characters.Character;
import game.artifacts.*;

public class Maze {
    private List<Room> rooms;

    private Maze() {
    }

    public int size() {
        return rooms.size();
    }

    public String toString() {
        return String.join("\n\n", rooms.stream().map(Object::toString).toList());
    }

    public Boolean hasLivingMonsters() {
        return rooms.stream().anyMatch(Room::hasLivingMonsters);
    }

    public Boolean hasLivingPlayer() {
        return rooms.stream().anyMatch(Room::hasLivingPlayer);
    }

    public List<Character> getLivingMonsters() {
        List<Character> monsters = new ArrayList<>();
        for (Room room : rooms) {
            monsters.addAll(room.getLivingMonsters());
        }
        return monsters;
    }

    public List<Character> getLivingCharacters() {
        List<Character> characters = new ArrayList<>();
        for (Room room : rooms) {
            characters.addAll(room.getLivingCharacters());
        }
        return characters;
    }

    public boolean hasLivingCharacters() {
        return getLivingCharacters().stream().anyMatch(character -> !character.isDead());
    }

    public Optional<Character> getLivingPlayer() {
        return getLivingCharacters().stream()
                .filter(Character::isPlayer).findFirst();
    }

    public List<Room> getRooms() {
        return List.copyOf(rooms);
    }

    public static Builder getNewBuilder(RoomFactory roomFactory) {
        return new Builder(roomFactory);
    }

    public Room getRoom(String roomName) {
        return rooms.stream()
                .filter(room -> room.getName().equals(roomName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No room with name " + roomName));
    }

    public static class Builder {
        private final Random rand = new Random();

        final Maze maze = new Maze();
        Map<String, Room> roomMap = new HashMap<>();
        private Boolean distributeRandomly = true;
        private boolean useBidirectionalConnections = true;
        private int currentRoomIndex = 0;
        private final RoomFactory roomFactory;

        private Builder(RoomFactory roomFactory) {
            this.roomFactory = roomFactory;
        }

        private Room nextRoom() {
            if (distributeRandomly) {
                return getRandomRoom();
            }
            return maze.getRooms().get(currentRoomIndex++ % maze.getRooms().size());
        }

        private Room getRandomRoom() {
            return maze.rooms.get(rand.nextInt(maze.rooms.size()));
        }

        public Builder createFullyConnectedRooms(List<String> roomNames) {
            maze.rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = roomFactory.createRoom(roomName);
                roomMap.put(currentRoom.getName(), currentRoom);
                maze.rooms.add(currentRoom);
            }
            fullyConnectRooms();
            return this;
        }

        public Builder createRooms(List<String> roomNames) {
            maze.rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = roomFactory.createRoom(roomName);
                roomMap.put(currentRoom.getName(), currentRoom);
                maze.rooms.add(currentRoom);
            }
            return this;
        }

        public Builder fullyConnectRooms() {
            for (Room room : maze.rooms) {
                for (Room otherRoom : maze.rooms) {
                    room.addNeighbor(otherRoom, true);
                }
            }
            return this;
        }

        public Builder createConnectedRooms(Integer minConnections, Integer numRooms) {
            createRooms(numRooms);
            connectRooms(minConnections);
            return this;
        }

        public void connectRooms(Integer minConnections) {
            if (maze.size() < 2) {
                return;
            }
            int realMinimumConnections = Math.min(minConnections, Math.max(maze.size() - 1, 1));
            for (Room room : maze.rooms) {
                while (room.numberOfNeighbors() < realMinimumConnections) {
                    Room neighbor = nextRoom();
                    if (!room.equals(neighbor) && !room.hasNeighbor(neighbor)) {
                        room.addNeighbor(neighbor, useBidirectionalConnections);
                    }
                }
            }
        }

        public Builder add(Character character) {
            nextRoom().add(character);
            return this;
        }

        public Builder addPlayer(Character player){
            nextRoom().add(player);
            return this;
        }

        public Builder addCharacters(List<Character> characters) {
            for (Character character : characters) {
                nextRoom().add(character);
            }
            return this;
        }

        public Builder addFood(List<Food> foodItems) {
            for (Food foodItem : foodItems) {
                nextRoom().add(foodItem);
            }
            return this;
        }

        public Builder addWeapon(List<Weapon> weaponItems) {
            for (Weapon currentWeapon : weaponItems) {
                nextRoom().add(currentWeapon);
            }
            return this;
        }

        public Maze build() {
            assert maze.size() > 0;
            for (Room room : maze.rooms) {
                if (room.numberOfNeighbors() == 0) {
                    System.out.println("Room "+room.getName()+" has no neighbors. Connecting it to another room.");
                    room.addNeighbor(nextRoom(), useBidirectionalConnections);
                }
            }
            if (!maze.hasLivingPlayer()) {
                System.out.println("No adventurers created. Terminating game.");
                throw new IllegalStateException("No adventurers created. Terminating game.");
            }
            return maze;
        }

        public Builder addToRoom(String roomName, Character character) {
            getRoom(roomName).add(character);
            return this;
        }

        public Builder addToRoom(String roomName, Food foodItem) {
            getRoom(roomName).add(foodItem);
            return this;
        }

        public Room getRoom(String roomName) {
            return roomMap.get(roomName);
        }

        public Builder distributeSequentially() {
            distributeRandomly = false;
            return this;
        }

        public Builder distributeRandomly() {
            distributeRandomly = true;
            return this;
        }

        public Builder setConnectionDirection(Boolean useBidirectionalConnections) {
            this.useBidirectionalConnections = useBidirectionalConnections;
            return this;
        }

        public Builder createNRoomsWithMConnections(int numberOfRooms, int numberOfConnections) {
            createConnectedRooms(numberOfConnections, numberOfRooms);
            return this;
        }

        private Builder createRooms(int numberOfRooms) {
            maze.rooms = new ArrayList<>();
            IntStream.range(0, numberOfRooms).forEach(_ -> {
                Room currentRoom = roomFactory.createRoom();
                roomMap.put(currentRoom.getName(), currentRoom);
                maze.rooms.add(currentRoom);
            });
            return this;
        }
    }
}
