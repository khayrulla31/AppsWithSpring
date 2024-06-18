package com.nikolay.dateApp.services;

import com.nikolay.dateApp.entities.DTO.PersonPageable;
import com.nikolay.dateApp.entities.DTO.PersonResponse;
import com.nikolay.dateApp.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    PersonPageable findAll(Pageable pageable);
    PersonPageable findAllASC(Pageable pageable);
    PersonPageable findAllDESC(Pageable pageable);
    PersonPageable findAllByCounty(Pageable pageable, String country);
    PersonPageable findAllByName(Pageable pageable, String name);

    PersonResponse findById(Integer id);
    //PersonResponse savePerson(Person person);

    PersonResponse savePerson(Person person, Integer userID);
    PersonResponse updatePerson(Integer id,Person person);
    void deletePerson(Integer id);
}
