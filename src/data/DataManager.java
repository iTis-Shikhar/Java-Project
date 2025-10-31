package data;

import models.*; // Import all classes from the models package
import java.io.*; // For file reading/writing
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    // Define the paths to our data files.
    private static final String VEHICLES_FILE_PATH = "data_files/vehicles.csv";
    private static final String USERS_FILE_PATH = "data_files/users.csv";
    private static final String BOOKINGS_FILE_PATH = "data_files/bookings.csv";

    /**
     * Reads the vehicles.csv file and returns a list of Vehicle objects.
     */
    public static List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        // This is a "try-with-resources" block. It automatically closes the file reader.
        try (BufferedReader reader = new BufferedReader(new FileReader(VEHICLES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Split the CSV line into parts

                String vehicleId = data[0];
                String vehicleType = data[1];
                String source = data[2];
                String destination = data[3];
                String departureTime = data[4];
                int totalSeats = Integer.parseInt(data[5]);

                // Create the correct object based on the vehicle type
                switch (vehicleType) {
                    case "Train":
                        vehicles.add(new Train(vehicleId, source, destination, departureTime, totalSeats));
                        break;
                    case "Bus":
                        vehicles.add(new Bus(vehicleId, source, destination, departureTime, totalSeats));
                        break;
                    case "Flight":
                        vehicles.add(new Flight(vehicleId, source, destination, departureTime, totalSeats));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading vehicles file: " + e.getMessage());
        }
        return vehicles;
    }

    /**
     * Reads the users.csv file and returns a list of User objects.
     */
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int userId = Integer.parseInt(data[0]);
                String username = data[1];
                String password = data[2];
                users.add(new User(userId, username, password));
            }
        } catch (IOException e) {
            System.out.println("Error loading users file: " + e.getMessage());
        }
        return users;
    }

    /**
     * Reads the bookings.csv file and returns a list of Booking objects.
     */
    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String bookingId = data[0];
                int userId = Integer.parseInt(data[1]);
                String vehicleId = data[2];
                String status = data[3];
                bookings.add(new Booking(bookingId, userId, vehicleId, status));
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings file: " + e.getMessage());
        }
        return bookings;
    }

    // --- Methods for SAVING data ---
    // We will call these methods when the application closes.

    /**
     * Saves the list of users to the users.csv file.
     */
    public static void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (User user : users) {
                writer.write(user.toCsvString());
                writer.newLine(); // Add a new line after each entry
            }
        } catch (IOException e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }

    /**
     * Saves the list of bookings to the bookings.csv file.
     */
    public static void saveBookings(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE_PATH))) {
            for (Booking booking : bookings) {
                writer.write(booking.toCsvString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings file: " + e.getMessage());
        }
    }
}