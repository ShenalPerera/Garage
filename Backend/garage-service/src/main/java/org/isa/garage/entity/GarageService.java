package org.isa.garage.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "service")
public class GarageService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "service_name",nullable = false)
    private String serviceName;

    @Column(name = "duration",nullable = false)
    private long duration;

    @ManyToMany(mappedBy = "garageServices")
    private List<Schedule> schedules;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getDuration() {
        return duration;
    }

    public GarageService() {
    }

    public GarageService(String serviceName, long duration) {
        this.serviceName = serviceName;
        this.duration = duration;
    }
}
