package org.isa.garage.dto;

public class BookingStatusDTO {
    private int bookingID;
    private boolean status;

    public BookingStatusDTO(int bookingID, boolean status) {
        this.bookingID = bookingID;
        this.status = status;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookingConfirmDTO{" +
                "bookingID=" + bookingID +
                ", status=" + status +
                '}';
    }
}
