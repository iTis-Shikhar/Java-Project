package models;

// This is an "abstract" class. It's a blueprint for other classes.
// You cannot create an object directly from an abstract class.
public abstract class Vehicle {
    // 'protected' means these fields can be accessed by this class and its children.
    protected String vehicleId;    // e.g., "12401" for a train, "UK07-1234" for a bus, "6E-2045" for a flight
    protected String vehicleType;  // "Train", "Bus", or "Flight"
    protected String source;
    protected String destination;
    protected String departureTime;
    protected int totalSeats;
    protected int availableSeats;

    // This is the constructor. It's used to initialize the object.
    public Vehicle(String vehicleId, String vehicleType, String source, String destination, String departureTime, int totalSeats) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // When a vehicle is created, all seats are available.
    }

    // --- These are called "Getters". They allow us to read the values of the fields. ---
    public String getVehicleId() { return vehicleId; }
    public String getVehicleType() { return vehicleType; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }

    // --- This is a "Setter". It allows us to change the value of a field. ---
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    // A helpful method for displaying vehicle info in a user-friendly way.
    @Override
    public String toString() {
        return "Type: " + vehicleType +
               " | ID: " + vehicleId +
               " | From: " + source + " To: " + destination +
               " | Departs: " + departureTime +
               " | Seats Available: " + availableSeats;
    }

    // A method to convert vehicle data into a comma-separated string for file saving.
    public String toCsvString() {
        return vehicleId + "," + vehicleType + "," + source + "," + destination + "," + departureTime + "," + totalSeats;
    }
}