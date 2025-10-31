package models;

public class Booking {
    private String bookingId; // e.g., "PNR123"
    private int userId;
    private String vehicleId; // Changed from trainNumber to vehicleId
    private String status;    // "CONFIRMED" or "WAITLIST"

    public Booking(String bookingId, int userId, String vehicleId, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public int getUserId() { return userId; }
    public String getVehicleId() { return vehicleId; } // Changed getter
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String toCsvString() {
        return bookingId + "," + userId + "," + vehicleId + "," + status;
    }
}