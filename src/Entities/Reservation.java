package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private static int idCounter = 1;
    private static List<Reservation> reservations = new ArrayList<>();

    private int id;
    private Room room;
    private User user;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(Room room, User user, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must not be null");
        }
        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        this.id = idCounter++;
        this.room = room;
        this.user = user;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        reservations.add(this);
        room.addReservation(this);
        user.addReservation(this);
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Room getRoom() {return room;}

    public void setRoom(Room room) {this.room = room;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public LocalDate getCheckInDate() {return checkInDate;}

    public void setCheckInDate(LocalDate checkInDate) {this.checkInDate = checkInDate;}

    public LocalDate getCheckOutDate() {return checkOutDate;}

    public void setCheckOutDate(LocalDate checkOutDate) {this.checkOutDate = checkOutDate;}

    public static Reservation findById(int id) {
        for (Reservation reservation: reservations){
            if(reservation.getId() == id){
                return reservation;
            }
        }
        return null;
    }

    public static List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public static Reservation create(Room room, User user, LocalDate checkInDate, LocalDate checkOutDate) {
        return new Reservation(room, user, checkInDate, checkOutDate);
    }

    public static boolean update(int id, Room newRoom, User newUser, LocalDate newCheckInDate, LocalDate newCheckOutDate) {
        Reservation reservation = findById(id);

        if (reservation == null) {
            return false;
        }
        if (newCheckInDate == null || newCheckOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must not be null");
        }
        if (newCheckOutDate.isBefore(newCheckInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        reservation.setRoom(newRoom);
        reservation.setUser(newUser);
        reservation.setCheckInDate(newCheckInDate);
        reservation.setCheckOutDate(newCheckOutDate);

        return true;
    }

    public static boolean delete(int id) {
        Reservation reservation = findById(id);

        if (reservation == null) {
            return false;
        }
        reservation.getRoom().removeReservation(reservation);
        reservation.getUser().deleteReservation(reservation);
        return reservations.remove(reservation);
    }
}
