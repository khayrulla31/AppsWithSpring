package com.nikolay.dateApp.services;

import com.nikolay.dateApp.entities.DTO.ChangePasswordForm;
import com.nikolay.dateApp.entities.DTO.RegisterForm;
import com.nikolay.dateApp.entities.DTO.UserPageable;
import com.nikolay.dateApp.entities.DTO.UserResponse;
import com.nikolay.dateApp.entities.User;
import org.springframework.data.domain.Pageable;

public interface UserServices {

    User findByUsername(String username);
    String createNewUser(RegisterForm registerForm);
    UserPageable findAll(Pageable pageable);
    UserResponse updatePassword(ChangePasswordForm changePasswordForm);
    String updateName(RegisterForm registerForm);
    //UserResponse findUserById(int Id);
    User findUserById(int Id);
    Boolean checkIfExistByName(String name);
    UserResponse findUserByName(String userName);
    void deleteUser();

    void deleteUser(int userID);
}
