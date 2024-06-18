package com.nikolay.dateApp.controllers.api;

import com.nikolay.dateApp.entities.DTO.UserPageable;
import com.nikolay.dateApp.entities.DTO.UserResponse;
import com.nikolay.dateApp.entities.User;
import com.nikolay.dateApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/")//admin: banana password - 1234
public class AdminControllerAPI {
    @Autowired
    private UserServices userServices;

    @GetMapping("getAllUsers")//работает
    public ResponseEntity<UserPageable> getUser(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                @RequestParam(defaultValue = "5", required = false) int pageSize){
        return new ResponseEntity<>(userServices.findAll(PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }
    @GetMapping("findById/{userId}")//работает
    public ResponseEntity<User> findUserByID(@PathVariable int userId){

        return new ResponseEntity<>(userServices.findUserById(userId), HttpStatus.FOUND);
    }
    @GetMapping("findByName/{userName}")//работает
    public ResponseEntity<UserResponse> findUserByName(@PathVariable String userName){
            if (userServices.findByUsername(userName) == null){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<>(userServices.findUserByName(userName), HttpStatus.FOUND);
    }
    @DeleteMapping(path = "deleteUserById/{userID}")
    public ResponseEntity<String> deleteUserById(@PathVariable  int userID){
        User user = userServices.findUserById(userID);
        userServices.deleteUser(userID);
        return new ResponseEntity<>("Пользователь "+user.getUserName()+" был удален!", HttpStatus.NO_CONTENT);
    }
}
