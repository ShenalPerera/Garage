package org.isa.garage.service;

import org.isa.garage.controller.GarageRestController;
import org.isa.garage.dao.UserDaoImpl;
import org.isa.garage.dto.UserSignupDTO;
import org.isa.garage.exception.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(GarageRestController.class);
    private final UserDaoImpl userDao;

    public UserService(UserDaoImpl userDao){
        this.userDao = userDao;
    }
    public boolean saveUser(UserSignupDTO userSignupDTO){
        if (userDao.existByEmail(userSignupDTO.getEmail())){
            logger.info("Email : {} already exist",userSignupDTO.getEmail());
            throw new UserAlreadyExistException("User already exist with email : " + userSignupDTO.getEmail());
        }
        return userDao.saveUser(userSignupDTO);
    }
}
