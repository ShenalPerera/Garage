package org.isa.garage.dto;

import java.sql.Date;
import java.sql.Time;

public class BookingCreateDTO {
    private int scheduleId;

    private String customerName;
    private String vehicleType;
    private int serviceId;
    private Date bookingDate;
    private Time bookingTime;

    public BookingCreateDTO() {
    }

    public BookingCreateDTO(int scheduleId, String customerName,String vehicleType, int serviceId, Date bookingDate, Time bookingTime) {
        this.scheduleId = scheduleId;
        this.customerName = customerName;
        this.vehicleType = vehicleType;
        this.serviceId = serviceId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Time getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Time bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "BookingCreateDTO{" +
                "scheduleId=" + scheduleId +
                ", customerName='" + customerName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", serviceId=" + serviceId +
                ", bookingDate=" + bookingDate +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
