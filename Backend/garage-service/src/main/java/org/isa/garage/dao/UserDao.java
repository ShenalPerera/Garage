package org.isa.garage.dao;

import org.isa.garage.dto.UserDTO;
import org.isa.garage.dto.UserSignupDTO;

import java.util.List;


public interface UserDao {

    boolean existByEmail(String email);
    boolean saveUser(UserSignupDTO userSignupDTO);
    UserDTO loadUserFromEmail(String email);
    List<UserDTO> getAllUsers();
    void updateUserDetails(UserDTO userDTO);

    UserDTO getUserById(Integer id);

    Boolean updatePassword(UserDTO userDTO);
}
