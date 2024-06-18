package com.nikolay.dateApp.repositories;

import com.nikolay.dateApp.entities.DTO.PersonPageable;
import com.nikolay.dateApp.entities.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
   Page<Person> findAllByOrderByAgeAsc(Pageable pageable);
   Page<Person> findAllByOrderByAgeDesc(Pageable pageable);
   Page<Person> findAllByCountryIgnoreCase(Pageable pageable, String country);
   Page<Person> findAllByNameIgnoreCase(Pageable pageable, String name);








}
