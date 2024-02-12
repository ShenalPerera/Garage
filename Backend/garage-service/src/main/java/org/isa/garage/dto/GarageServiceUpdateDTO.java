package org.isa.garage.dto;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class GarageServiceUpdateDTO {
    @NotNull(message = "ID field is null")
    private Integer id;
    @NotBlank(message = "service name is cannot be blank")
    private String serviceName;
    @Min(value = 30, message = "Duration must be greater than or equal to 30 min")
    private long duration;

    public GarageServiceUpdateDTO(Integer id, String serviceName, long duration) {
        this.id = id;
        this.serviceName = serviceName;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
