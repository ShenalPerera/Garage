package org.isa.garage.dao;

import org.isa.garage.dto.UserSignupDTO;
import org.springframework.stereotype.Repository;


public interface UserDao {

    boolean saveUser(UserSignupDTO userSignupDTO);
}
