package com.nikolay.dateApp.controllers;

import com.nikolay.dateApp.entities.DTO.PersonRequest;
import com.nikolay.dateApp.entities.DTO.PersonResponse;
import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.entities.User;
import com.nikolay.dateApp.exeptions.PersonNotFoundExeption;
import com.nikolay.dateApp.repositories.PersonRepository;
import com.nikolay.dateApp.repositories.UserRepository;
import com.nikolay.dateApp.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
//import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/second")
public class PageController {

    private PersonService personService;
    private PersonRepository personRepository;
    private UserRepository userRepository;
    @Autowired
    public PageController(PersonService personService,
                          PersonRepository personRepository,
                          UserRepository userRepository
    ) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/user/{id}")
    public String snow(@PathVariable("id") Integer id, Model model){
        Person person = personRepository.findById(id).orElseThrow(()->new PersonNotFoundExeption("Person not found!!!"));
        model.addAttribute("userNow", person);

        return "profile";
    }
    @GetMapping
    public String secondPage(){
        return "secondPage";
    }

    @GetMapping("/form")
   // @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String form(@ModelAttribute("secondP") PersonRequest personRequest){
        return "form";
    }

    @PostMapping//обрабатывает форму после нажатия кнопки ОТПРАВИТЬ (сохраняет Person в БД и отправляет успешную страницу)
    public String process(@ModelAttribute ("secondP")
                              //@Valid PersonRequest personRequest,
            @Valid Person person,
                          //Principal principal,
                           Errors errors){
        if (errors.hasErrors()){//проверка на наличие ошибок
            return "form";
        }
        //Person person=new Person();


        //Worksheet worksheet=new Worksheet();

       // Optional<User> checkIfExist = userRepository.findByUserName(personRequest.getName());
        //if (!checkIfExist.isPresent()){
        //    throw new PersonNotFoundExeption("USER DOESN'T EXIST");
        //}
        //User user2 = checkIfExist.get();
        //User user = userRepository.findByUserName(personRequest.getName()).get();
       // Integer userID = user2.getId();


     /*  person.setName(personRequest.getName());
       person.setSurname(personRequest.getSurname());
       person.setSex(personRequest.getSex());
       person.setAge(fullYearsOld(personRequest.getDateOfBirth()));//количество полных лет на данный момент
       person.setCountry(personRequest.getCountry());
       person.setCity(personRequest.getCity());
       person.setDateOfBirth(new Date(personRequest.getDateOfBirth().getTime()));//из util.DAta преображаем в sql.Data
       person.setEmail(personRequest.getEmail());
       person.setHeight(personRequest.getHeight());
       person.setWeight(personRequest.getWeight());
       person.setAboutMe(personRequest.getAboutMe());*/

       //String nam = principal.getName();
       //person.setUser(user2);
        //Optional<User> user = userRepository.findByUserName(nam);
        //if (!user.isPresent()){
        //    throw new PersonNotFoundExeption("USER DOESN'T EXIST");
        //}
        //через springSecurity получаем активного пользователя и используем его ID
        User user3 = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        //person.setUser(user3);




       // worksheet.setPatronymic(personRequest.getPatronymic());



        //worksheet.setPerson(person);

        //personService.savePerson(person, userID);
        personService.savePerson(person, user3.getId());

        System.out.println("It was saved in database");

        //worksheetRepository.save(worksheet);

        return "seccessPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id){
        PersonResponse checkIfExist = personService.findById(id);
        if (checkIfExist==null){
            return "failPage";
        }
        personService.deletePerson(id);
        return "seccessPage";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * @param date
     * method to find out age
     * @return long
     */
    private static long fullYearsOld(java.util.Date date) {
            Instant nows = Instant.now();//текущее время/дата
            Instant ago = Instant.ofEpochMilli(date.getTime());//дата рождения
        return ChronoUnit.YEARS.between(ago.atZone(ZoneId.systemDefault()), nows.atZone(ZoneId.systemDefault()));
    }


}
