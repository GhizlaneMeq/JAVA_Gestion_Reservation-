import Entities.User;
import Entities.Room;
import Exceptions.RoomAlreadyExistException;
import Exceptions.UserAlreadyExistsException;
import Menus.LoginMenu;
import Menus.UserMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            initializeTestData();
        } catch (UserAlreadyExistsException | RoomAlreadyExistException e) {
            System.out.println("Error initializing data: " + e.getMessage());
        }

        while (true) {
            if (LoginMenu.getCurrentUser() == null) {
                LoginMenu.showLoginMenu(scanner);
            } else {
                User currentUser = LoginMenu.getCurrentUser();
                UserMenu.showUserMenu(scanner, currentUser);
            }
        }
    }

    private static void initializeTestData() throws UserAlreadyExistsException, RoomAlreadyExistException {
        User user1 = new User(1, "alice", "password123");
        User user2 = new User(2, "bob", "password456");

        Room room1 = new Room(1, "101", Room.Capacity.SINGLE);
        Room room2 = new Room(2, "102", Room.Capacity.DOUBLE);

        User.add(user1);
        User.add(user2);

        Room.add(room1);
        Room.add(room2);
    }
}
