package org.isa.garage.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserSignupDTO {
    @NotBlank(message = "Email is empty/not found")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is empty/notfound")
    @Size(min = 4 ,message = "Password should have least 4 characters")
    private String password;

    @NotBlank(message = "First name is empty/not found")
    private String firstname;

    @NotBlank(message = "Last name is empty/not found")
    private String lastname;


    public UserSignupDTO(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}

