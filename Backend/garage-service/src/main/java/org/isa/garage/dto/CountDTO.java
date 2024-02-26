package org.isa.garage.dto;

public class CountDTO {
    private  long serviceCount;
    private long bookingsCount;
    private long schedulesCount;

    public CountDTO() {
    }

    public CountDTO(long serviceCount, long bookingsCount, long schedulesCount) {
        this.serviceCount = serviceCount;
        this.bookingsCount = bookingsCount;
        this.schedulesCount = schedulesCount;
    }

    public long getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(long serviceCount) {
        this.serviceCount = serviceCount;
    }

    public long getBookingsCount() {
        return bookingsCount;
    }

    public void setBookingsCount(long bookingsCount) {
        this.bookingsCount = bookingsCount;
    }

    public long getSchedulesCount() {
        return schedulesCount;
    }

    public void setSchedulesCount(long schedulesCount) {
        this.schedulesCount = schedulesCount;
    }
}
