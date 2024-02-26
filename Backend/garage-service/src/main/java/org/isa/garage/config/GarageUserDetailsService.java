package org.isa.garage.config;

import org.isa.garage.dao.UserDaoImpl;
import org.isa.garage.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GarageUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(GarageUserDetailsService.class);
    private final UserDaoImpl userDao;
    @Autowired
    public GarageUserDetailsService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }


    @Override
    public GarageUserDetails loadUserByUsername(String username)  {
        final UserDTO userDTO = userDao.loadUserFromEmail(username);
        if (userDTO == null) {
            logger.info("User not found : {}", username);
            throw  new UsernameNotFoundException("User not found");
        }
        return new GarageUserDetails(userDTO);
    }


}
