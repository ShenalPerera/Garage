package org.isa.garage.service;

import org.apache.tomcat.websocket.AuthenticationException;
import org.isa.garage.dao.UserDao;
import org.isa.garage.dto.ChangePasswordDTO;
import org.isa.garage.dto.UserDTO;
import org.isa.garage.exception.UserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public AdminService(UserDao userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers(){
        return userDao.getAllUsers();
    }

    public Boolean changePassword(ChangePasswordDTO changePasswordDTO) throws AuthenticationException {
        UserDTO userDTO =  userDao.getUserById(changePasswordDTO.getId());

        if (userDTO == null)
            throw new UsernameNotFoundException("User not found");

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())){
                throw new UserException("Passwords do not match!");
        }

        if (passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), userDTO.getPassword())){
            System.out.println(changePasswordDTO.getNewPassword());
            userDTO.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            return userDao.updatePassword(userDTO);
        }
        throw new AuthenticationException("Your are not authenticated!");
    }
}
