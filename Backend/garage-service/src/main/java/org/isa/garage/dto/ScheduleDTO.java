package org.isa.garage.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ScheduleDTO {
    private Integer id;
    private Time startTime;
    private Time endTime;
    private Date date;
    private int maxCapacity;
    private int currentCapacity;
    private List<GarageServiceDTO> garageServices;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Integer id, Time startTime, Time endTime, Date date, int maxCapacity, int currentCapacity, List<GarageServiceDTO> garageServices) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.garageServices = garageServices;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public List<GarageServiceDTO> getGarageServices() {
        return garageServices;
    }

    public void setGarageServices(List<GarageServiceDTO> garageServices) {
        this.garageServices = garageServices;
    }
}
