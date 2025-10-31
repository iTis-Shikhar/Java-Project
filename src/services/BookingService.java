package services;

import models.Booking;
import models.User;
import models.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingService {

    public static List<Vehicle> searchVehicles(String source, String destination, List<Vehicle> allVehicles) {
        return allVehicles.stream()
                .filter(v -> v.getSource().equalsIgnoreCase(source) && v.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    public static Booking bookTicket(Vehicle vehicle, User user, List<Booking> allBookings) {
        String bookingId = "PNR" + System.currentTimeMillis();
        Booking newBooking;

        if (vehicle.getAvailableSeats() > 0) {
            vehicle.setAvailableSeats(vehicle.getAvailableSeats() - 1);
            newBooking = new Booking(bookingId, user.getUserId(), vehicle.getVehicleId(), "CONFIRMED");
        } else {
            System.out.println("No seats available. You will be added to the waitlist.");
            newBooking = new Booking(bookingId, user.getUserId(), vehicle.getVehicleId(), "WAITLIST");
        }
        allBookings.add(newBooking);
        return newBooking;
    }

    public static List<Booking> getMyBookings(User user, List<Booking> allBookings) {
        return allBookings.stream()
                .filter(b -> b.getUserId() == user.getUserId())
                .collect(Collectors.toList());
    }

    public static boolean cancelTicket(String bookingId, List<Booking> allBookings, List<Vehicle> allVehicles) {
        Optional<Booking> bookingToCancelOpt = allBookings.stream()
                .filter(b -> b.getBookingId().equalsIgnoreCase(bookingId))
                .findFirst();

        if (bookingToCancelOpt.isPresent()) {
            Booking bookingToCancel = bookingToCancelOpt.get();
            if (bookingToCancel.getStatus().equals("CONFIRMED")) {
                Optional<Vehicle> associatedVehicleOpt = allVehicles.stream()
                        .filter(v -> v.getVehicleId().equals(bookingToCancel.getVehicleId()))
                        .findFirst();
                if (associatedVehicleOpt.isPresent()) {
                    Vehicle vehicle = associatedVehicleOpt.get();
                    Optional<Booking> waitlistedBookingOpt = allBookings.stream()
                            .filter(b -> b.getVehicleId().equals(vehicle.getVehicleId()) && b.getStatus().equals("WAITLIST"))
                            .min(Comparator.comparing(Booking::getBookingId)); 

                    if (waitlistedBookingOpt.isPresent()) {
                        Booking bookingToPromote = waitlistedBookingOpt.get();
                        bookingToPromote.setStatus("CONFIRMED");
                    } else {
                        vehicle.setAvailableSeats(vehicle.getAvailableSeats() + 1);
                    }
                }
            }
            allBookings.remove(bookingToCancel);
            return true;
        }
        return false;
    }
}
