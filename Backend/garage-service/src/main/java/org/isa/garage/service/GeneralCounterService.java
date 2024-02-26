package org.isa.garage.service;

import org.isa.garage.dto.CountDTO;
import org.springframework.stereotype.Service;

@Service
public class GeneralCounterService {

    private final ScheduleService scheduleService;
    private final BookingService bookingService;
    private final GarageServicesHandlerService garageServicesHandlerService;

    public GeneralCounterService(ScheduleService scheduleService, BookingService bookingService, GarageServicesHandlerService garageServicesHandlerService) {
        this.scheduleService = scheduleService;
        this.bookingService = bookingService;
        this.garageServicesHandlerService = garageServicesHandlerService;
    }

    public CountDTO getCountOfSources(){
        return new CountDTO(garageServicesHandlerService.getCount(),bookingService.getCount(),scheduleService.getCount());
    }


}
