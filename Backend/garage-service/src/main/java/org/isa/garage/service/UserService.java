package org.isa.garage.service;

import org.isa.garage.config.GarageUserDetails;
import org.isa.garage.controller.GarageRestController;
import org.isa.garage.dao.UserDaoImpl;
import org.isa.garage.dto.JWTResponseDTO;
import org.isa.garage.dto.UserLoginDTO;
import org.isa.garage.dto.UserSignupDTO;
import org.isa.garage.exception.UserAlreadyExistException;
import org.isa.garage.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDaoImpl userDao;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDaoImpl userDao, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(UserSignupDTO userSignupDTO) {
        if (userDao.existByEmail(userSignupDTO.getEmail())) {
            logger.info("Email : {} already exist", userSignupDTO.getEmail());
            throw new UserAlreadyExistException("User already exist with email : " + userSignupDTO.getEmail());
        }
        userSignupDTO.setPassword(passwordEncoder.encode(userSignupDTO.getPassword()));
        return userDao.saveUser(userSignupDTO);
    }

    public JWTResponseDTO login(UserLoginDTO userLoginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

        if (authentication.isAuthenticated()) {
            GarageUserDetails garageUserDetails = (GarageUserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(garageUserDetails);
            return new JWTResponseDTO(token, garageUserDetails.getUsername());
        }

        return null;
    }
}
