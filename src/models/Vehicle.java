package models;

public abstract class Vehicle {
    protected String vehicleId;
    protected String vehicleType;
    protected String source;
    protected String destination;
    protected String departureTime;
    protected int totalSeats;
    protected int availableSeats;

    public Vehicle(String vehicleId, String vehicleType, String source, String destination, String departureTime, int totalSeats) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; 
    }

    public String getVehicleId() { return vehicleId; }
    public String getVehicleType() { return vehicleType; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Type: " + vehicleType +
               " | ID: " + vehicleId +
               " | From: " + source + " To: " + destination +
               " | Departs: " + departureTime +
               " | Seats Available: " + availableSeats;
    }

    public String toCsvString() {
        return vehicleId + "," + vehicleType + "," + source + "," + destination + "," + departureTime + "," + totalSeats;
    }
}
