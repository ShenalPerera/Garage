package org.isa.garage.controller;

import org.apache.tomcat.websocket.AuthenticationException;
import org.isa.garage.dao.UserDaoImpl;
import org.isa.garage.dto.ChangePasswordDTO;
import org.isa.garage.dto.UserDTO;
import org.isa.garage.service.AdminService;
import org.isa.garage.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Validated @RequestBody ChangePasswordDTO changePasswordDTO) throws AuthenticationException {
        return new ResponseEntity<>(adminService.changePassword(changePasswordDTO),HttpStatus.OK);
    }
}
