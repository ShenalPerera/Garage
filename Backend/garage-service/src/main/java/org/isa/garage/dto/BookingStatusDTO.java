package org.isa.garage.dto;

public class BookingConfirmDTO {
    private int bookingID;
    private boolean status;

    public BookingConfirmDTO(int bookingID, boolean status) {
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
