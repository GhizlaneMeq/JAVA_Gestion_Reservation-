package Entities;

import java.util.ArrayList;
import java.util.List;
public class Room {
    private int id;
    private String roomNumber;
    private String capacity;
    private boolean isAvailable;
    private List<Reservation> reservations = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();

    public Room(int id,String roomNumber, String roomType) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = roomType;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String roomType) { this.capacity = roomType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public List<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    public  void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }


    public static Room findById(int id) {
        return rooms.stream()
                .filter(room -> room.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public static List<Room> findAll() {
        return new ArrayList<>(rooms);
    }
    public static void add(Room room) {
        int newId = rooms.isEmpty() ? 1 : rooms.stream().mapToInt(Room::getId).max().orElse(0) + 1;
        room.setId(newId);
        rooms.add(room);
    }

    public static boolean update(Room updatedRoom) {
        for (Room room : rooms) {
            if (room.getId() == updatedRoom.getId()) {
                room.setRoomNumber(updatedRoom.getRoomNumber());
                room.setCapacity(updatedRoom.getCapacity());
                room.setAvailable(updatedRoom.isAvailable());
                return true;
            }
        }
        return false;
    }
    public static boolean delete(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                rooms.remove(room);
                return true;
            }
        }
        return false;
    }
    public static boolean isAvailable(int id) {
        Room room = findById(id);
        return room != null && room.isAvailable();
    }
}
