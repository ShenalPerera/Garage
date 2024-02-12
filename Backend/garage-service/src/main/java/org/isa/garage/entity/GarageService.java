package org.isa.garage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
public class GarageService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "service_name",nullable = false)
    private String serviceName;

    @Column(name = "duration",nullable = false)
    private long duration;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
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
