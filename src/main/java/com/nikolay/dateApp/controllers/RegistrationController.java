package com.nikolay.dateApp.controllers;

import com.nikolay.dateApp.entities.DTO.ChangePasswordForm;
import com.nikolay.dateApp.entities.DTO.RegisterForm;
import com.nikolay.dateApp.entities.DTO.ChangePasswordForm;
import com.nikolay.dateApp.entities.Role;
import com.nikolay.dateApp.entities.User;
import com.nikolay.dateApp.repositories.RoleRepository;
import com.nikolay.dateApp.repositories.UserRepository;
import com.nikolay.dateApp.security.UserService;
import com.nikolay.dateApp.services.UserServices;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
@Log4j2
@Controller
@RequestMapping("/auth/")//user orange password 123
public class RegistrationController {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    private UserServices userServices;

    private AuthenticationManager authenticationManager;
    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder,
                                  UserRepository userRepository,
                                  UserServices userServices,
                                  AuthenticationManager authenticationManager,

    RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.userServices = userServices;
    }
    @GetMapping("register")
     public String getRegisterForm(Model model){
        model.addAttribute("userRegister", new RegisterForm());
        return "registerForm";
    }
    @PostMapping("register")
    public String sendRegisterForm(@ModelAttribute("userRegister") @Valid RegisterForm registerForm,
                                      Errors error,
                                      Model model){
        //проверяем наличие пользователя с таки же именем
        if (userExists(registerForm.getUsername())){
         //  для API  throw new PersonNotFoundExeption("User has same name "+registerForm.getUsername()+" Was registered already");
            System.out.println("Уже существует пользователь такой");
            return "redirect:register";
        }

        if (error.hasErrors()){
                return "registerForm";
        }
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())){

            return "redirect:register";
        }

        User newUs= new User();
        newUs.setUserName(registerForm.getUsername());
        newUs.setPassword(passwordEncoder.encode(registerForm.getPassword()));

        //Role role = roleRepository.findByName("USER").get();

        Role role = roleRepository.findByName("ADMIN").get();

        newUs.setRoles(Collections.singletonList(role));//singleton(role));

        userRepository.save(newUs);
        log.info("СОЗДАН "+ newUs.getUserName(),"успешно");
        //здесь обрататывать нужно если уже есть такой юзер
        return "seccessPage";
    }

    private boolean userExists(String username) {
        Optional<User>exist = userRepository.findByUserName(username);
        if (!exist.isPresent()){
            return false;
        }

        return true;
    }
    @GetMapping("changePassword")
    public String changePassword(Model model)
    {
        model.addAttribute("OldPass", new ChangePasswordForm());

        return "changePasswordStepOne";
    }

    @PostMapping("changePassword")
    public String confirmPassword(@ModelAttribute("OldPass") @Valid ChangePasswordForm changePasswordForm,
                                  Errors errors,
                                  Model model){

        if (errors.hasErrors()){//проверка на наличие ошибок
            return "changePasswordStepOne";
        }

        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        System.out.println(user.getPassword()+" password"+"    "+changePasswordForm.getOldPassword());

        /*if (!user.getPassword().equals(changePasswordForm.getOldPassword())){
            return "failPage";
        }*/

        if (!passwordEncoder.matches(changePasswordForm.getOldPassword(), user.getPassword())){
            System.out.println("FUCKKKKKKKKK 2");
            return "failPage";
        }else {
            System.out.println("Success");
        }
        //newUs.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        //user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));

        if (changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmNewPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
        }

        //Role role = roleRepository.findByName("USER").get();
        //Role role = roleRepository.findByName("ADMIN").get();
        //user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return "seccessPage";
    }

    public String alreadyExist(Model model){
        model.addAttribute("alredyExist","EXIST");
        return "";
    }
}
