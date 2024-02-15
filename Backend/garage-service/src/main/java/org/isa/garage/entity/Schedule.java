package org.isa.garage.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Column(name = "date",nullable = false)
    private Date date;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Column(name = "current_capacity", nullable = false)
    private int currentCapacity;

    @ManyToMany
    @JoinTable(name = "schedule_service",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<GarageService> garageServices;

    public Schedule(Integer id, Time startTime, Time endTime, Date date, int maxCapacity, int currentCapacity, List<GarageService> garageServices) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.garageServices = garageServices;
    }

    public Schedule() {
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

    public List<GarageService> getGarageServices() {
        return garageServices;
    }

    public void setGarageServices(List<GarageService> garageServices) {
        this.garageServices = garageServices;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", maxCapacity=" + maxCapacity +
                ", currentCapacity=" + currentCapacity +
                '}';
    }
}
