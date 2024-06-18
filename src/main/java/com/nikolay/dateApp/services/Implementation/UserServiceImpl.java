package com.nikolay.dateApp.services.Implementation;

import com.nikolay.dateApp.entities.DTO.*;
import com.nikolay.dateApp.entities.Role;
import com.nikolay.dateApp.entities.User;
import com.nikolay.dateApp.exeptions.PersonNotFoundExeption;
import com.nikolay.dateApp.repositories.RoleRepository;
import com.nikolay.dateApp.repositories.UserRepository;
import com.nikolay.dateApp.services.UserServices;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
@Log4j2
@Service
public class UserServiceImpl implements UserServices {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User>exist = userRepository.findByUserName(username);
        if (!exist.isPresent()){
            return  null;
        }
        User existingUser = exist.get();
        return existingUser;
    }

    @Override
    public String createNewUser(RegisterForm registerForm) {
       if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())){
            log.info("пароли не совпадают");
            throw new PersonNotFoundExeption("Your password don't equal password which have to confirm his!! Attempt again");
        }
      //Optional<User>exist = userRepository.findByUserName(registerForm.getUsername());
       //checkIfExistByName(registerForm.getUsername());
     // if (exist.isPresent()){
          if (!checkIfExistByName(registerForm.getUsername())){
          log.info("пользователь с именем "+registerForm.getUsername()+" уже существует");
          throw new PersonNotFoundExeption("User with name = " +registerForm.getUsername()+" already exist!! Attempt again with another name");
      }

        User user = new User();
        user.setUserName(registerForm.getUsername());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));//.setRoles(Collections.singleton(role));//

        userRepository.save(user);
        log.info("создан новый пользователь: "+registerForm.getUsername());

        return user.getUserName();
    }

    @Override
    public UserPageable findAll(Pageable pageable) {//работает
        Page<User> userPage  = userRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        UserPageable userPageable =new UserPageable();

        userPageable.setListOfPerson(userPage.stream().map(p->PersonMapper.PERSON_MAPPER.fromUserToResponse(p)).collect(Collectors.toList()));
        userPageable.setPageNo(userPage.getNumber());
        userPageable.setPageSize(userPage.getSize());
        userPageable.setLast(userPage.isLast());
        userPageable.setTotalPages(userPage.getTotalPages());
        userPageable.setTotalElements(userPage.getTotalElements());

        return userPageable;
    }

    @Override
    public String updateName(RegisterForm registerForm) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        if (registerForm.getUsername() != null){
            user.setUserName(registerForm.getUsername());
        }
        userRepository.save(user);

        return user.getUserName();
    }

    @Override//работает!!!
    public UserResponse updatePassword(@Valid ChangePasswordForm changePasswordForm) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        if (passwordEncoder.matches(changePasswordForm.getOldPassword(), user.getPassword())){
            if (changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmNewPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
                userRepository.save(user);
            }
        }
         return PersonMapper.PERSON_MAPPER.fromUserToResponse(userRepository.save(user));
        }

    @Override
    //public UserResponse findUserById(int Id) {
    public User findUserById(int Id) {
        User user = userRepository.findById(Id).orElseThrow(()->new PersonNotFoundExeption("User with this ID doesn't exist!!"));
        //return PersonMapper.PERSON_MAPPER.fromUserToResponse(user);
        return user;
    }

    @Override
    public Boolean checkIfExistByName(String name) {
        boolean checkIfExist = userRepository.findByUserName(name).isPresent();
        if (!checkIfExist){
            return true;
        }
        return false;
    }

    @Override
    public UserResponse findUserByName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->new PersonNotFoundExeption("User with NAME like this doesn't exist!!"));
        return PersonMapper.PERSON_MAPPER.fromUserToResponse(user);
    }

    @Override
    public void deleteUser() {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        userRepository.deleteById(user.getId());
    }
    @Override
    public void deleteUser(int userID) {
        userRepository.deleteById(userID);
    }

}
