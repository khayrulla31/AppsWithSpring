package com.nikolay.dateApp.controllers;

import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.exeptions.PersonNotFoundExeption;
import com.nikolay.dateApp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("second/user")
public class UserController {
    private PersonRepository personRepository;
    @Autowired
    public UserController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @GetMapping("/user/{id}")
    public String snow(@PathVariable("id") Integer id, Model model){
        Person person = personRepository.findById(id).orElseThrow(()->new PersonNotFoundExeption("Person not found!!!"));
        model.addAttribute("userNow", person);

        return "profile";
    }
    @GetMapping("/admin")
    public String snowAdmin(){
        return "adminPage";
    }
}
