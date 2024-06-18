package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.User;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegisterForm implements Serializable {
    @NotBlank(message = "please, put your name")
    @Size(min = 3, message = "it must have greater than 3 ")
    private String username;

    @NotBlank(message = "enter your password")
    @Size(min = 3, message = "more then 3")
    private String password;

    @NotBlank(message = "confirm your password")
    @Transient
    private String confirmPassword;

   //public User toUser(PasswordEncoder passwordEncoder){
   //     return new User(username, passwordEncoder.encode(password));
    //}
}
