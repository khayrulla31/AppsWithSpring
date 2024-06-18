package com.nikolay.dateApp.repositories;

import com.nikolay.dateApp.entities.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.NONE)
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void repositorySavePersonTest(){
    //Person person1=new Person(10,"Stas","Ivanov",LocalDate.of(2000,12,12));


   //Person savedPerson= personRepository.save(person1);

    //Assertions.assertThat(savedPerson).isNotNull();
    //Assertions.assertThat(savedPerson.getId()).isGreaterThan(0);
}
    @Test
    public void repositoryFindAllTest(){
/*

        Person person1=new Person(10,"Stas","Ivanov",LocalDate.of(2000,12,12));
        Person person2=new Person(199,"Igor","Petrov",LocalDate.of(1994,10,1));
        Person person3=new Person(33,"natasha","Lyen",LocalDate.of(1965,8,29));
*/

        //personRepository.save(person1);
        //personRepository.save(person2);
      //  personRepository.save(person3);

        List<Person>listPerson=personRepository.findAll();

        Assertions.assertThat(listPerson).isNotNull();
        Assertions.assertThat(listPerson.size()).isEqualTo(3);
    }

    @Test
    public void repositoryFindById(){
        //Person person1=new Person(1,"Stas","Ivanov",LocalDate.of(2000,12,12));

        //personRepository.save(person1);
       // Person person=personRepository.findById(person1.getId()).get();

        //Assertions.assertThat(person).isNotNull();
    }
    @Test
    public void repositoryDeleteById(){
       // Person person1=new Person(10,"Stas","Ivanov",LocalDate.of(2000,12,12));
/*
        personRepository.save(person1);

        personRepository.deleteById(person1.getId());

        Optional<Person>deletedPerson=personRepository.findById(person1.getId());

        Assertions.assertThat(deletedPerson).isEmpty();*/
    }



}