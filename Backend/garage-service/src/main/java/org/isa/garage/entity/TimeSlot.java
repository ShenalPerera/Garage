package org.isa.garage.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "time_slot")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    @JoinTable(name = "time_slot_service",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services;


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
