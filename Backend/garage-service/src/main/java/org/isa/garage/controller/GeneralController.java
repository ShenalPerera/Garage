package org.isa.garage.controller;

import org.isa.garage.dto.CountDTO;
import org.isa.garage.service.GeneralCounterService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GeneralController {

    private final GeneralCounterService generalCounterService;

    public GeneralController(GeneralCounterService generalCounterService) {
        this.generalCounterService = generalCounterService;
    }

    @GetMapping("/get-counts")
    public CountDTO getCounts(){
        return generalCounterService.getCountOfSources();
    }
}
