package org.isa.garage.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Time;

public class SingleServiceScheduleCreateDTO {
    @NotNull(message = "Start time is not found")
    private Time startTime;
    @NotNull(message = "Start time is not found")
    private Time endTime;
    @NotNull(message = "Start time is not found")
    @FutureOrPresent(message = "Invalid date")
    private Date date;
    @NotNull(message = "Service ID field not found")
    private Integer serviceId;

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

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
