package models;

public class Flight extends Vehicle {
    public Flight(String vehicleId, String source, String destination, String departureTime, int totalSeats) {
        super(vehicleId, "Flight", source, destination, departureTime, totalSeats);
    }
}