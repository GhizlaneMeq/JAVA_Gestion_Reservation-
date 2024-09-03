package Entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private List<Reservation> reservations = new ArrayList<Reservation>();
    public User(int id,String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
}

