package org.isa.garage.controller;

import jakarta.validation.Valid;
import org.isa.garage.dto.UserLoginDTO;
import org.isa.garage.dto.UserSignupDTO;
import org.isa.garage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GarageRestController {
    private static final Logger logger = LoggerFactory.getLogger(GarageRestController.class);


    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public GarageRestController(UserService userService){
        this.userService  =userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupDTO userSignupDTO){
        logger.info("Signup user {}",userSignupDTO.getFirstname());
        boolean created = userService.saveUser(userSignupDTO);
        if (created)
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(),userLoginDTO.getPassword())
        );

        if (authentication.isAuthenticated()){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
