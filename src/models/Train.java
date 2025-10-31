package models;

public class Train extends Vehicle {
    public Train(String vehicleId, String source, String destination, String departureTime, int totalSeats) {
        super(vehicleId, "Train", source, destination, departureTime, totalSeats);
    }
}
