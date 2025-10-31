package services;

import models.Booking;
import models.User;
import models.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService {

    /**
     * Searches for available vehicles based on source and destination.
     */
    public static List<Vehicle> searchVehicles(String source, String destination, List<Vehicle> allVehicles) {
        return allVehicles.stream()
                .filter(v -> v.getSource().equalsIgnoreCase(source) && v.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    /**
     * Books a ticket. If seats are available, the booking is CONFIRMED.
     * If not, the user is added to the WAITLIST.
     */
    public static Booking bookTicket(Vehicle vehicle, User user, List<Booking> allBookings) {
        String bookingId = "PNR" + System.currentTimeMillis();
        Booking newBooking;

        if (vehicle.getAvailableSeats() > 0) {
            // If seats are available, confirm the booking
            vehicle.setAvailableSeats(vehicle.getAvailableSeats() - 1);
            newBooking = new Booking(bookingId, user.getUserId(), vehicle.getVehicleId(), "CONFIRMED");
        } else {
            // --- NEW WAITLIST LOGIC ---
            // If no seats, add to waitlist
            System.out.println("No seats available. You will be added to the waitlist.");
            newBooking = new Booking(bookingId, user.getUserId(), vehicle.getVehicleId(), "WAITLIST");
        }
        allBookings.add(newBooking);
        return newBooking;
    }

    /**
     * Retrieves all bookings made by a specific user.
     */
    public static List<Booking> getMyBookings(User user, List<Booking> allBookings) {
        return allBookings.stream()
                .filter(b -> b.getUserId() == user.getUserId())
                .collect(Collectors.toList());
    }

    /**
     * Cancels a ticket. If the cancelled ticket was CONFIRMED,
     * it promotes the first person from the waitlist for that vehicle.
     */
    public static boolean cancelTicket(String bookingId, List<Booking> allBookings, List<Vehicle> allVehicles) {
        Optional<Booking> bookingToCancelOpt = allBookings.stream()
                .filter(b -> b.getBookingId().equalsIgnoreCase(bookingId))
                .findFirst();

        if (bookingToCancelOpt.isPresent()) {
            Booking bookingToCancel = bookingToCancelOpt.get();

            // --- UPGRADED CANCELLATION LOGIC ---
            if (bookingToCancel.getStatus().equals("CONFIRMED")) {
                // Find the vehicle to manage its seat count
                Optional<Vehicle> associatedVehicleOpt = allVehicles.stream()
                        .filter(v -> v.getVehicleId().equals(bookingToCancel.getVehicleId()))
                        .findFirst();

                if (associatedVehicleOpt.isPresent()) {
                    Vehicle vehicle = associatedVehicleOpt.get();
                    
                    // Find the first person on the waitlist for this specific vehicle
                    Optional<Booking> waitlistedBookingOpt = allBookings.stream()
                            .filter(b -> b.getVehicleId().equals(vehicle.getVehicleId()) && b.getStatus().equals("WAITLIST"))
                            .min(Comparator.comparing(Booking::getBookingId)); // Get the oldest waitlisted booking

                    if (waitlistedBookingOpt.isPresent()) {
                        // If there's a waitlisted person, promote them
                        Booking bookingToPromote = waitlistedBookingOpt.get();
                        bookingToPromote.setStatus("CONFIRMED");
                        //System.out.println("A waitlisted passenger (Booking ID: " + bookingToPromote.getBookingId() + ") has been confirmed.");
                    } else {
                        // If no one is on the waitlist, free up the seat
                        vehicle.setAvailableSeats(vehicle.getAvailableSeats() + 1);
                    }
                }
            }
            
            // Finally, remove the cancelled booking from the list
            allBookings.remove(bookingToCancel);
            return true;
        }
        return false;
    }
}