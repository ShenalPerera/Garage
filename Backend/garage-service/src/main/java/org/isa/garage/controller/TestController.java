package org.isa.garage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String testGetMethod(@RequestBody Map<String,String> requestBody){
        String email = requestBody.get("email");
        logger.info("email : {}",email);
        String password = requestBody.get("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        if (authentication.isAuthenticated()) return "Sucesss";
        return "This is test method";
    }

}
