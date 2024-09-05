package Menus;

import Entities.User;
import Exceptions.UserAlreadyExistsException;

import java.util.Scanner;

public class LoginMenu {
    private static User currentUser = null;

    public static void showLoginMenu(Scanner scanner) {
        System.out.println("\nHotel Reservation Management");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> login(scanner);
            case 2 -> signup(scanner);
            case 3 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = User.findByUsername(username);
        if (user == null) {
            System.out.println("Invalid username.");
        } else if (!user.validatePassword(password)) {
            System.out.println("Invalid password.");
        } else {
            currentUser = user;
            System.out.println("Login successful!");
        }
    }

    private static void signup(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            User user = new User(User.findAll().size() + 1, username, password);
            User.add(user);
            currentUser = user;
            System.out.println("Sign-up successful! You are now logged in.");
        } catch (UserAlreadyExistsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
