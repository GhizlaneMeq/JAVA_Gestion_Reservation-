package Entities;

import Exceptions.RoomAlreadyExistException;

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
        for(Room room: rooms){
            if(room.getId() == id){
                return room;
            }
        }
        return null;
    }
    public static List<Room> findAll() {
        return new ArrayList<>(rooms);
    }

    public static void add(Room room) throws RoomAlreadyExistException {
        for (Room r : rooms) {
            if (r.getRoomNumber().equals(room.getRoomNumber()) && r.getCapacity() == room.getCapacity()) {
                throw new RoomAlreadyExistException("A room with this number and capacity already exists.");
            }
        }
        int newId = 1;
        if (!rooms.isEmpty()) {
            for (Room r : rooms) {
                if (r.getId() >= newId) {
                    newId = r.getId() + 1;
                }
            }
        }
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
