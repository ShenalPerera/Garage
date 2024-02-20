package org.isa.garage.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ScheduleEditDTO {
    @NotNull(message = "Schedule id is not valid")
    private Integer id;
    @NotNull(message = "Start time is not found")
    private Time startTime;
    @NotNull(message = "Start time is not found")
    private Time endTime;
    @NotNull(message = "Start time is not found")
    @FutureOrPresent(message = "Invalid date")
    private Date date;
    @NotNull(message = "Service IDs not found")
    private List<Integer> serviceId;
//    @NotNull(message = "Max capacity is not found")
//    @Min(value = 1, message = "Max capacity should have value greater than 0")
    private int maxCapacity;


    public ScheduleEditDTO(Integer id, Time startTime, Time endTime, Date date, List<Integer> serviceId, int maxCapacity) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.serviceId = serviceId;
        this.maxCapacity = maxCapacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Integer> getServiceId() {
        return serviceId;
    }

    public void setServiceId(List<Integer> serviceId) {
        this.serviceId = serviceId;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
