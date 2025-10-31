package models;

public class Booking {
    private String bookingId; 
    private int userId;
    private String vehicleId; 
    private String status;  

    public Booking(String bookingId, int userId, String vehicleId, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public String getVehicleId() { return vehicleId; } 
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String toCsvString() {
        return bookingId + "," + userId + "," + vehicleId + "," + status;
    }
}
