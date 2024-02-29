package org.isa.garage.controller;

import jakarta.validation.Valid;
import org.isa.garage.dto.*;
import org.isa.garage.service.GarageServicesHandlerService;
import org.isa.garage.service.ScheduleService;
import org.isa.garage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class GarageUserController {
    private static final Logger logger = LoggerFactory.getLogger(GarageUserController.class);

    private final KafkaTemplate<String, BookingStatusDTO> kafkaTemplate;
    private final UserService userService;

    private final ScheduleService scheduleService;

    private final GarageServicesHandlerService garageServicesHandlerService;
    public GarageUserController(UserService userService, ScheduleService scheduleService, GarageServicesHandlerService garageServicesHandlerService,KafkaTemplate<String, BookingStatusDTO> kafkaTemplate) {
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.garageServicesHandlerService = garageServicesHandlerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupDTO userSignupDTO) {
        logger.info("Signup user {}", userSignupDTO.getFirstname());
        boolean created = userService.saveUser(userSignupDTO);
        if (created) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        logger.info("Login with email : {}", userLoginDTO.getEmail());

        JWTResponseDTO jwtResponseDTO = userService.login(userLoginDTO);
        logger.info("Successfully Logged in");
        return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
    }


    @GetMapping("/get-user")
    public ResponseEntity<?> getUser() throws AuthenticationException {
        return new ResponseEntity<>(userService.getUser(),HttpStatus.OK);
    }

    @PutMapping("/change-user-details")
    public ResponseEntity<?> changeUserDetails(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.changeUserDetails(userDTO),HttpStatus.OK);
    }

}
