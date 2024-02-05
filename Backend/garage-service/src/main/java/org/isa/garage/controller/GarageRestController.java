package org.isa.garage.controller;

import org.isa.garage.dao.UserDao;
import org.isa.garage.dto.UserSignupDTO;
import org.isa.garage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GarageRestController {
    private static final Logger logger = LoggerFactory.getLogger(GarageRestController.class);


    private final UserService userService;


    public GarageRestController(UserService userService){
        this.userService  =userService;
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody UserSignupDTO userSignupDTO){
        return userService.saveUser(userSignupDTO);
    }
}
