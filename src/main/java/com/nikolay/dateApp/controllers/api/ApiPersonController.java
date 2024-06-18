package com.nikolay.dateApp.controllers.api;

import com.nikolay.dateApp.entities.DTO.PersonPageable;
import com.nikolay.dateApp.entities.DTO.PersonResponse;
import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/", produces = "application/json")//path="second "-обрабатывает запросы с там же путем,
//НО из-за produces = "application/json" если не требуется ответ в формате JSON обрабытывает html разметку если есть
//но все конечные точки работает findAllPersons и findPersonById
@CrossOrigin(origins = {"http://localhost:8080"})
public class ApiPersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("person")//получить всех Persons
    public ResponseEntity<PersonPageable> getAllPerson(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "5", required = false) int pageSize)
    {
        return new ResponseEntity<>(personService.findAll(PageRequest.of(pageNo,pageSize)), HttpStatus.OK);
    }

    @GetMapping("person/asc")//получить всех Persons с сортировкой
    public ResponseEntity<PersonPageable> sortAsc(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize){
        PersonPageable personPageable = personService.findAllASC(PageRequest.of(pageNo, pageSize));
        if (pageNo>personPageable.getTotalPages()){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(personPageable, HttpStatus.FOUND);
    }

    @GetMapping("person/desc")//получить всех Persons с ОБРАТНОЙ сортировкой
    public ResponseEntity<PersonPageable> sortDesc(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                   @RequestParam(defaultValue = "10", required = false) int pageSize)
    {
        return new ResponseEntity<>(personService.findAllDESC(PageRequest.of(pageNo, pageSize)),HttpStatus.FOUND);
    }

    @GetMapping("person/findByCountry/{country}")//поиск всех PErosn по определенной стране!!НЕ ЗАВИСИТ ОТ РЕГИСТРА
    public ResponseEntity<PersonPageable> fidingByCountry(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                         @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                         @PathVariable(required = true) String country)
    {
        return new ResponseEntity<>(personService.findAllByCounty(PageRequest.of(pageNo,pageSize), country), HttpStatus.OK);
    }
    @GetMapping("person/findByName/{name}")//поиск всех PErosn по определенной стране!!НЕ ЗАВИСИТ ОТ РЕГИСТРА
    public ResponseEntity<PersonPageable> findByNameInPage(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @PathVariable String name)
    {
        if (personService.findAllByName(PageRequest.of(pageNo, pageSize), name).getListOfPerson().isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personService.findAllByName(PageRequest.of(pageNo, pageSize), name), HttpStatus.OK);
    }

    @GetMapping("person/{id}")//поиск по ID
    public ResponseEntity<PersonResponse> findPersonById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.findById(id));
    }
    @PostMapping(path = "person/{userID}/create",//создаем Person для User по userID
            consumes = "application/json")
    public ResponseEntity<PersonResponse> savePerson(@RequestBody Person person,
                                                     @PathVariable Integer userID){

        return new ResponseEntity<>(personService.savePerson(person, userID),HttpStatus.CREATED);
    }

   @PatchMapping(path = "person/{id}",
                 consumes = "application/json")
   public ResponseEntity<PersonResponse> updatePerson(
                                     @PathVariable Integer id,
                                     @RequestBody Person person){

       return new ResponseEntity<>(personService.updatePerson(id,person),HttpStatus.ACCEPTED);
   }
    @DeleteMapping("person/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Integer id){
            personService.deletePerson(id);
        return new ResponseEntity<>("Person was successfully deleted !",HttpStatus.NO_CONTENT);
   }
}
