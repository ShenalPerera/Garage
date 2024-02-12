package org.isa.garage.controller;

import jakarta.validation.Valid;
import org.isa.garage.dto.JWTResponseDTO;
import org.isa.garage.dto.UserLoginDTO;
import org.isa.garage.dto.UserSignupDTO;
import org.isa.garage.service.GarageServicesHandlerService;
import org.isa.garage.service.ScheduleService;
import org.isa.garage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GarageUserController {
    private static final Logger logger = LoggerFactory.getLogger(GarageUserController.class);


    private final UserService userService;

    private final ScheduleService scheduleService;

    private final GarageServicesHandlerService garageServicesHandlerService;
    public GarageUserController(UserService userService, ScheduleService scheduleService, GarageServicesHandlerService garageServicesHandlerService) {
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.garageServicesHandlerService = garageServicesHandlerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupDTO userSignupDTO) {
        logger.info("Signup user {}", userSignupDTO.getFirstname());
        boolean created = userService.saveUser(userSignupDTO);
        if (created) return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        logger.info("Login with email : {}", userLoginDTO.getEmail());

        JWTResponseDTO jwtResponseDTO = userService.login(userLoginDTO);
        logger.info("Successfully Logged in");
        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
    }

}
