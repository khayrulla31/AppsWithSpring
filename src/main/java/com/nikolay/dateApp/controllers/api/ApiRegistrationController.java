package com.nikolay.dateApp.controllers.api;

import com.nikolay.dateApp.entities.DTO.ChangePasswordForm;
import com.nikolay.dateApp.entities.DTO.RegisterForm;
import com.nikolay.dateApp.services.UserServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Log4j2
@RestController
@RequestMapping(path = "/regist/", produces = "application/json")
public class ApiRegistrationController {
    @Autowired
    private UserServices userServices;
    @PostMapping("createUser")//работает

    public ResponseEntity<String> createUser(@RequestBody RegisterForm registerForm){

       if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())){
            return new ResponseEntity<>("Пароль и подтверждение пароля не совпадают",HttpStatus.BAD_REQUEST);
        }
       if (!userServices.checkIfExistByName(registerForm.getUsername())){
            return new ResponseEntity<>("Пользователь с именем " +registerForm.getUsername()+" уже существует!! Попробуйте с еще раз с другим именем", HttpStatus.NOT_ACCEPTABLE);
        }
       log.info("создан новый пользователь: "+registerForm.getUsername());
        return new ResponseEntity<>(userServices.createNewUser(registerForm)+registerForm.getUsername()+"!, вы успешно зарегистрированы!", HttpStatus.CREATED);
    }
    @PatchMapping(path = "changePassword",
            consumes = "application/json")
    public ResponseEntity<String> updatePassword(
            @RequestBody ChangePasswordForm changePasswordForm){

        return new ResponseEntity<>(userServices.updatePassword(changePasswordForm)+" пароль успешно изменен",HttpStatus.ACCEPTED);
    }
    @PatchMapping(path = "changeName",
            consumes = "application/json")
    public ResponseEntity<String> updateName(
            @RequestBody RegisterForm registerForm){

        return new ResponseEntity<>(userServices.updateName(registerForm)+" имя успешно изменено",HttpStatus.ACCEPTED);
    }
    @DeleteMapping("deleteUser")//работает
    public ResponseEntity<String> deleteUser(){
        userServices.deleteUser();
        log.info("ПОльзователь удален");
        return new ResponseEntity<>("Ваша учетная запись была удалена", HttpStatus.NO_CONTENT);
    }

}
