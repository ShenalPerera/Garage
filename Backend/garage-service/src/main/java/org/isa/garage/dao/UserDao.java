package org.isa.garage.dao;

import org.isa.garage.dto.UserDTO;
import org.isa.garage.dto.UserSignupDTO;


public interface UserDao {

    boolean existByEmail(String email);
    boolean saveUser(UserSignupDTO userSignupDTO);
    UserDTO loadUserFromEmail(String email);
}
