package org.isa.garage.service;

import org.isa.garage.dto.GarageServiceDTO;
import org.isa.garage.dto.GarageServiceUpdateDTO;
import org.isa.garage.entity.GarageService;
import org.isa.garage.entity.Schedule;
import org.isa.garage.exception.GarageServiceException;
import org.isa.garage.exception.GarageServiceNotFoundException;
import org.isa.garage.repository.GarageServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        return garageServiceRepository.findAll().stream().parallel().map(garageService -> new GarageServiceDTO(garageService.getId(), garageService.getServiceName(), garageService.getDuration())).collect(Collectors.toList());
    }

    public GarageServiceDTO createGarageService(GarageServiceDTO garageServiceDTO) {
        GarageService garageService = garageServiceRepository.save(new GarageService(garageServiceDTO.getServiceName(), garageServiceDTO.getDuration()));
        return new GarageServiceDTO(garageService.getId(), garageService.getServiceName(), garageService.getDuration());
    }

    public GarageServiceDTO editGarageService(GarageServiceUpdateDTO garageServiceUpdateDTO) {
        Optional<GarageService> garageServiceOptional = garageServiceRepository.findById(garageServiceUpdateDTO.getId());

        GarageService garageService = garageServiceOptional.orElseThrow(() -> new GarageServiceNotFoundException("No service found with id:" + garageServiceUpdateDTO.getId()));

        if (!garageService.getSchedules().isEmpty())
            throw new GarageServiceException("This Service is attached with a schedule!");

        garageService.setServiceName(garageServiceUpdateDTO.getServiceName());
        garageService.setDuration(garageServiceUpdateDTO.getDuration());

        GarageService updatedGarageService = garageServiceRepository.save(garageService);

        return new GarageServiceDTO(updatedGarageService.getId(), updatedGarageService.getServiceName(), updatedGarageService.getDuration());
    }

    public void deleteGarageService(Integer id){
        GarageService garageService = garageServiceRepository.findById(id).orElseThrow(()-> new GarageServiceException("Service not found!"));
        if (!garageService.getSchedules().isEmpty())
            throw new GarageServiceException("This Service is attached with a schedule!");
        garageServiceRepository.deleteById(id);
    }

    public GarageService getServiceById(Integer id){
        Optional<GarageService> service =  garageServiceRepository.findById(id);

        return service.orElseThrow(()->new GarageServiceNotFoundException("Service can not be found"));
    }

    public List<GarageService> getServicesByIdList(List<Integer> ids){
        return garageServiceRepository.findAllByIdIn(ids);
    }

    public Integer getMaxServiceDurationFromGivenIds(List<Integer> ids){
        return garageServiceRepository.findMaxServiceDurationByIdIn(ids);
    }

    public Long getCount(){
        return garageServiceRepository.count();
    }

}
