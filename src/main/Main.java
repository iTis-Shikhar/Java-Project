package main;

import data.DataManager;
import models.*;
import services.AuthenticationService;
import services.BookingService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<User> users;
    private static List<Vehicle> vehicles;
    private static List<Booking> bookings;

    public static void main(String[] args) {
        users = DataManager.loadUsers();
        vehicles = DataManager.loadVehicles();
        bookings = DataManager.loadBookings();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n========================================");
            System.out.println("|   Welcome to the Reservation System  |");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.println("----------------------------------------");
            System.out.print("Please enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
                continue;
            }

            switch (choice) {
                case 1:
                    handleLogin(scanner);
                    break;
                case 2:
                    handleSignUp(scanner);
                    break;
                case 3:
                    System.out.println("Saving data... Thank you for using the system!");
                    DataManager.saveUsers(users);
                    DataManager.saveBookings(bookings);
                    scanner.close();
                    System.exit(0); 
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleLogin(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User loggedInUser = AuthenticationService.login(username, password, users);

        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
            showUserDashboard(loggedInUser, scanner); 
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    private static void handleSignUp(Scanner scanner) {
        System.out.print("Enter a new username: ");
        String username = scanner.next();
        System.out.print("Enter a new password: ");
        String password = scanner.next();

        User newUser = AuthenticationService.signUp(username, password, users);

        if (newUser != null) {
            System.out.println("Sign up successful! You can now log in.");
        } 
    }

    private static void showUserDashboard(User loggedInUser, Scanner scanner) {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. Search & Book a Ticket");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel a Ticket");
            System.out.println("4. Logout");
            System.out.println("----------------------");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter source: ");
                    String source = scanner.next();
                    System.out.print("Enter destination: ");
                    String destination = scanner.next();

                    List<Vehicle> availableVehicles = BookingService.searchVehicles(source, destination, vehicles);
                    if (availableVehicles.isEmpty()) {
                        System.out.println("No vehicles found for that route.");
                    } else {
                        System.out.println("Available vehicles:");
                        for (int i = 0; i < availableVehicles.size(); i++) {
                            System.out.println((i + 1) + ". " + availableVehicles.get(i));
                        }

                        System.out.print("Select a vehicle to book (enter number, or 0 to go back): ");
                        try {
                            int vehicleChoice = scanner.nextInt();
                            if (vehicleChoice > 0 && vehicleChoice <= availableVehicles.size()) {
                                Vehicle selectedVehicle = availableVehicles.get(vehicleChoice - 1);
                                Booking newBooking = BookingService.bookTicket(selectedVehicle, loggedInUser, bookings);
                                if (newBooking.getStatus().equals("CONFIRMED")) {
                                    System.out.println("Booking successful! Your PNR is: " + newBooking.getBookingId());
                                } else {
                                    System.out.println("Added to waitlist. Your PNR is: " + newBooking.getBookingId());
                                }
                            } else if (vehicleChoice != 0) {
                                System.out.println("Invalid selection.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.next();
                        }
                    }
                    break;
                case 2: 
                    List<Booking> myBookings = BookingService.getMyBookings(loggedInUser, bookings);
                    if (myBookings.isEmpty()) {
                        System.out.println("You have no bookings.");
                    } else {
                        System.out.println("Your Bookings:");
                        for (Booking b : myBookings) {
                            System.out.println("PNR: " + b.getBookingId() + " | Vehicle ID: " + b.getVehicleId()
                                    + " | Status: " + b.getStatus());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter the PNR of the booking to cancel: ");
                    String pnrToCancel = scanner.next();
                    boolean success = BookingService.cancelTicket(pnrToCancel, bookings, vehicles);
                    if (success) {
                        System.out.println("Booking cancelled successfully.");
                    } else {
                        System.out.println("Cancellation failed. PNR not found.");
                    }
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
