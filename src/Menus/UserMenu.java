package Menus;

import Entities.Reservation;
import Entities.Room;
import Entities.User;
import Exceptions.RoomAlreadyExistException;

import java.time.LocalDate;
import java.util.Scanner;

public class UserMenu {

    public static void showUserMenu(Scanner scanner, User currentUser) {
        System.out.println("\nHotel Reservation Management");
        System.out.println("1. View User Info");
        System.out.println("2. View Rooms");
        System.out.println("3. View My Reservations");
        System.out.println("4. Add Room");
        System.out.println("5. Create Reservation");
        System.out.println("6. Update Reservation");
        System.out.println("7. Cancel Reservation");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> displayUserInfo(currentUser);
            case 2 -> displayRooms();
            case 3 -> displayUserReservations(currentUser);
            case 4 -> addRoom(scanner);
            case 5 -> createReservation(scanner, currentUser);
            case 6 -> updateReservation(scanner, currentUser);
            case 7 -> deleteReservation(scanner);
            case 8 -> {
                LoginMenu.setCurrentUser(null);
                System.out.println("You have been logged out.");
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    private static void displayUserInfo(User currentUser) {
        if (currentUser == null) {
            System.out.println("You need to be logged in to view your information.");
            return;
        }

        System.out.println("User Information:");
        System.out.printf("ID: %d%n", currentUser.getId());
        System.out.printf("Username: %s%n", currentUser.getUsername());
    }

    private static void displayRooms() {
        System.out.println("List of Rooms:");
        Room.findAll().forEach(room -> {
            System.out.printf("ID: %d, Number: %s, Capacity: %s%n",
                    room.getId(), room.getRoomNumber(), room.getCapacity());
        });
    }

    private static void displayUserReservations(User currentUser) {
        if (currentUser == null) {
            System.out.println("You need to be logged in to view your reservations.");
            return;
        }

        System.out.println("Your Reservations:");

        for (Reservation reservation : Reservation.findAll()) {
            if (reservation.getUser().equals(currentUser)) {
                System.out.printf("ID: %d, Room: %s, Check-In: %s, Check-Out: %s%n",
                        reservation.getId(),
                        reservation.getRoom().getRoomNumber(),
                        reservation.getCheckInDate(),
                        reservation.getCheckOutDate());
            }
        }
    }


    private static void addRoom(Scanner scanner) {
        System.out.print("Room Number: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Capacity (SUITE, DOUBLE, SINGLE): ");
        String capacityStr = scanner.nextLine();
        Room.Capacity capacity;

        try {
            capacity = Room.Capacity.valueOf(capacityStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid capacity type. Please enter SUITE, DOUBLE, or SINGLE.");
            return;
        }

        Room room = new Room(0, roomNumber, capacity);

        try {
            Room.add(room);
            System.out.println("Room successfully added.");
        } catch (RoomAlreadyExistException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createReservation(Scanner scanner, User currentUser) {
        if (currentUser == null) {
            System.out.println("You need to be logged in to create a reservation.");
            return;
        }

        System.out.print("Room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine();
        Room room = Room.findById(roomId);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.print("Check-In Date (YYYY-MM-DD): ");
        LocalDate checkInDate;
        System.out.print("Check-Out Date (YYYY-MM-DD): ");
        LocalDate checkOutDate;

        try {
            checkInDate = LocalDate.parse(scanner.nextLine());
            checkOutDate = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }

        if (room.isRoomAvailable(checkInDate, checkOutDate, -1)) {
            Reservation.create(room, currentUser, checkInDate, checkOutDate);
            System.out.println("Reservation successfully created.");
        } else {
            System.out.println("The room is not available for the selected dates.");
        }
    }

    private static void updateReservation(Scanner scanner, User currentUser) {
        if (currentUser == null) {
            System.out.println("You need to be logged in to update a reservation.");
            return;
        }

        System.out.print("Reservation ID: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        Reservation reservation = Reservation.findById(reservationId);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        System.out.print("New Room ID: ");
        int newRoomId = scanner.nextInt();
        scanner.nextLine();
        Room newRoom = Room.findById(newRoomId);

        if (newRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.print("New Check-In Date (YYYY-MM-DD): ");
        LocalDate newCheckInDate;
        System.out.print("New Check-Out Date (YYYY-MM-DD): ");
        LocalDate newCheckOutDate;

        try {
            newCheckInDate = LocalDate.parse(scanner.nextLine());
            newCheckOutDate = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }

        if (newRoom.isRoomAvailable(newCheckInDate, newCheckOutDate, reservationId)) {
            Reservation.update(reservationId, newRoom, currentUser, newCheckInDate, newCheckOutDate);
            System.out.println("Reservation successfully updated.");
        } else {
            System.out.println("The room is not available for the new dates.");
        }
    }

    private static void deleteReservation(Scanner scanner) {
        System.out.print("Reservation ID to cancel: ");
        int reservationId = scanner.nextInt();
        scanner.nextLine();

        if (Reservation.delete(reservationId)) {
            System.out.println("Reservation successfully canceled.");
        } else {
            System.out.println("Reservation not found.");
        }
    }
}
