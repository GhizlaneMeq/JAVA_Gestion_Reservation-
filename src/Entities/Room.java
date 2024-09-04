package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public enum Capacity {
        SUITE,
        DOUBLE,
        SINGLE
    }
    private int id;
    private String roomNumber;
    private Capacity capacity;
    private List<Reservation> reservations = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();

    public Room(int id, String roomNumber, Capacity capacity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getRoomNumber() {return roomNumber;}

    public void setRoomNumber(String roomNumber) {this.roomNumber = roomNumber;}

    public Capacity getCapacity() {return capacity;}

    public void setCapacity(Capacity capacity) {this.capacity = capacity;}

    public List<Reservation> getReservations() {return reservations;}

    public void setReservations(List<Reservation> reservations) {this.reservations = reservations;}

    public void addReservation(Reservation reservation) {reservations.add(reservation);}

    public void removeReservation(Reservation reservation) {reservations.remove(reservation);}

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
    public boolean isRoomAvailable(LocalDate startDate, LocalDate endDate, int reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() != reservationId)
                .noneMatch(reservation ->
                        !(endDate.isBefore(reservation.getCheckInDate())
                                || startDate.isAfter(reservation.getCheckOutDate()))
                );
    }



}
