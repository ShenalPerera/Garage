package org.isa.garage.controller;

import jakarta.validation.Valid;
import org.isa.garage.dto.GarageServiceDTO;
import org.isa.garage.dto.GarageServiceUpdateDTO;
import org.isa.garage.entity.GarageService;
import org.isa.garage.service.GarageServicesHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class GarageServiceController {
    private static final Logger logger = LoggerFactory.getLogger(GarageServiceController.class);


    private final GarageServicesHandlerService garageServicesHandlerService;

    @Autowired
    public GarageServiceController(GarageServicesHandlerService garageServicesHandlerService){
        this.garageServicesHandlerService = garageServicesHandlerService;
    }

    @GetMapping("/get-services")
    public ResponseEntity<?> getAllServices(){
        return new ResponseEntity<>(garageServicesHandlerService.getAllServices(), HttpStatus.OK) ;
    }

    @PostMapping("/create-service")
    public ResponseEntity<?> createGarageService(@Valid @RequestBody GarageServiceDTO garageServiceDTO){
        return new ResponseEntity<>(garageServicesHandlerService.createGarageService(garageServiceDTO),HttpStatus.CREATED);
    }

    @PutMapping("/edit-service")
    public ResponseEntity<?> editGarageService(@Valid @RequestBody GarageServiceUpdateDTO garageServiceUpdateDTO){
        return new ResponseEntity<>(garageServicesHandlerService.editGarageService(garageServiceUpdateDTO),HttpStatus.CREATED);
    }
}
