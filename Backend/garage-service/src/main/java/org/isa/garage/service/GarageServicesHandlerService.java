package org.isa.garage.service;

import org.isa.garage.dto.GarageServiceDTO;
import org.isa.garage.dto.GarageServiceUpdateDTO;
import org.isa.garage.entity.GarageService;
import org.isa.garage.exception.GarageServiceNotFoundException;
import org.isa.garage.repository.GarageServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarageServicesHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(GarageServicesHandlerService.class);


    private final GarageServiceRepository garageServiceRepository;

    @Autowired
    public GarageServicesHandlerService(GarageServiceRepository garageServiceRepository) {
        this.garageServiceRepository = garageServiceRepository;
    }

    public List<GarageServiceDTO> getAllServices() {
        return garageServiceRepository.findAll().stream().parallel().map(garageService -> {
            return new GarageServiceDTO(garageService.getId(), garageService.getServiceName(), garageService.getDuration());
        }).collect(Collectors.toList());
    }

    public GarageServiceDTO createGarageService(GarageServiceDTO garageServiceDTO) {
        GarageService garageService = garageServiceRepository.save(new GarageService(garageServiceDTO.getServiceName(), garageServiceDTO.getDuration()));
        return new GarageServiceDTO(garageService.getId(), garageService.getServiceName(), garageService.getDuration());
    }

    public GarageServiceDTO editGarageService(GarageServiceUpdateDTO garageServiceUpdateDTO) {
        Optional<GarageService> garageServiceOptional = garageServiceRepository.findById(garageServiceUpdateDTO.getId());
        GarageService garageService = garageServiceOptional.orElseThrow(() -> new GarageServiceNotFoundException("No service found with id:" + garageServiceUpdateDTO.getId()));

        garageService.setServiceName(garageServiceUpdateDTO.getServiceName());
        garageService.setDuration(garageServiceUpdateDTO.getDuration());

        GarageService updatedGarageService = garageServiceRepository.save(garageService);

        return new GarageServiceDTO(updatedGarageService.getId(), updatedGarageService.getServiceName(), updatedGarageService.getDuration());
    }

    public void deleteGarageService(Integer id){
        garageServiceRepository.deleteById(id);
    }

}
