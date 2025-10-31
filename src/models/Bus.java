package models;

public class Bus extends Vehicle {
    public Bus(String vehicleId, String source, String destination, String departureTime, int totalSeats) {
        super(vehicleId, "Bus", source, destination, departureTime, totalSeats);
    }
}