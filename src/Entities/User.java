package Entities;

import Exceptions.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private List<Reservation> reservations = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    public User(int id, String username, String password) throws UserAlreadyExistsException {
        if (findByUsername(username) != null) {
            throw new UserAlreadyExistsException("Username already exists.");
        }
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean validatePassword(String inputPassword) {return this.password.equals(inputPassword);}
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
    public static User findById(int id) {
        for(User user: users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public static User findByUsername(String username) {
        for(User user: users){
            if(user.getUsername().equals(username)){
                return  user;
            }
        }
        return null;
    }
    public static List<User> findAll() {
        return new ArrayList<>(users);
    }
    public static void add(User user) {
        users.add(user);
    }
}
