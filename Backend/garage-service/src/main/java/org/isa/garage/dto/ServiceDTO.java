package org.isa.garage.dto;

public class ServiceDTO {
    private long id;
    private String serviceName;
    private long duration;

    // Constructors
    public ServiceDTO() {
    }

    public ServiceDTO(long id, String serviceName, long duration) {
        this.id = id;
        this.serviceName = serviceName;
        this.duration = duration;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}

