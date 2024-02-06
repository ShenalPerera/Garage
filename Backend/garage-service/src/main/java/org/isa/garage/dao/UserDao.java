package org.isa.garage.dao;

import org.isa.garage.dto.UserSignupDTO;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserDao {

    boolean existByEmail(String email);
    boolean saveUser(UserSignupDTO userSignupDTO);
}
