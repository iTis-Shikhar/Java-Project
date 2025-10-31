package models;

// 'extends Vehicle' means Train is a child of Vehicle and gets all its properties.
public class Train extends Vehicle {

    // The constructor for Train just needs to call the parent (Vehicle) constructor.
    public Train(String vehicleId, String source, String destination, String departureTime, int totalSeats) {
        // 'super(...)' calls the constructor of the parent class (Vehicle).
        super(vehicleId, "Train", source, destination, departureTime, totalSeats);
    }
}