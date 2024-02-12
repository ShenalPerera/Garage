package org.isa.garage.service;

import org.isa.garage.dto.ServiceDTO;
import org.isa.garage.repository.GarageServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarageServicesHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(GarageServicesHandlerService.class);


    private final GarageServiceRepository garageServiceRepository;

    @Autowired
    public GarageServicesHandlerService(GarageServiceRepository garageServiceRepository){
        this.garageServiceRepository = garageServiceRepository;
    }
    public List<ServiceDTO> getAllServices(){
        return garageServiceRepository.findAll()
                .stream().parallel()
                .map(service -> {
                    return new ServiceDTO(service.getId(), service.getServiceName(), service.getDuration());
                })
                .collect(Collectors.toList());
    }

}
