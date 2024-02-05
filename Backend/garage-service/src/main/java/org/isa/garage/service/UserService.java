package org.isa.garage.service;

import org.isa.garage.dao.UserDaoImpl;
import org.isa.garage.dto.UserSignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDaoImpl userDao;

    public UserService(UserDaoImpl userDao){
        this.userDao = userDao;
    }
    public boolean saveUser(UserSignupDTO userSignupDTO){
        return userDao.saveUser(userSignupDTO);
    }
}
