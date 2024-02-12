package org.isa.garage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class GarageServiceDTO {
    private long id;
    @NotBlank(message = "Service name is not valid")
    private String serviceName;
    @Min(value = 30, message = "Duration must be greater or equal than  30 min")
    private long duration;

    // Constructors
    public GarageServiceDTO() {
    }

    public GarageServiceDTO(long id, String serviceName, long duration) {
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

