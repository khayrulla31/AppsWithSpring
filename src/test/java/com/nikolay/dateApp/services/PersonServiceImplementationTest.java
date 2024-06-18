package com.nikolay.dateApp.services;

import com.nikolay.dateApp.entities.DTO.PersonRequest;
import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.repositories.PersonRepository;
import com.nikolay.dateApp.services.Implementation.PersonServiceImpl;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
@Data
@ExtendWith(MockitoExtension.class)// при работе с Mock обязательно объявлять
class PersonServiceImplementationTest{
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personServiceImplementation;
    

    @Test
    public void findAllPersonTest() {
         List<Person> persns = ourPersons();//  сначала приходит в метод лист PERSON и методе уже меняется

         Mockito.when(personRepository.findAll()).thenReturn(persns);
        // List<PersonResponse> result=personServiceImplementation.findAllPerson();//вот тут уже другой тип не Person, а PersonResponce

         //Assertions.assertNotNull(result);
         //Assertions.assertEquals(6,result.size());
    }
    @Test
    public void findByIdForPersonTest(){
        List<Person>persns=ourPersons();

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(persns.get(0)));
        //Optional<Person> manN1= personServiceImplementation.findByIdForPerson(1);

        //Assertions.assertNotNull(manN1);
    }

    @Test
    public void findByIdTest() {
        Person person=new Person();
        person.setName("Irina");
        person.setId(1);
        personRepository.save(person);

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(person));
        //PersonResponse pr1=personServiceImplementation.findById(1);

        //Assertions.assertNotNull(pr1);
    }
    @Test
    public void updatePersonTest(){
        Person person=new Person();
        person.setName("Irina");
        person.setId(1);
        personRepository.save(person);

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.ofNullable(person));
        Mockito.when(personRepository.save(person)).thenReturn(person);
        PersonRequest updatingBody=new PersonRequest();
        updatingBody.setName("Galla");
       // PersonResponse personResponse=personServiceImplementation.updatePerson(person.getId(),updatingBody);

       // Assertions.assertNotNull(personResponse);
       // Assert.assertNotEquals(person, personResponse);
        //Assertions.assertEquals("Galla", personResponse.getName());
        Assertions.assertEquals("Galla", person.getName());
    }
    @Test
    public void deletePersonTest(){
        Person person=new Person();
        person.setName("Irina");
        person.setId(1);

        personRepository.save(person);
        Integer num=1;
        Mockito.doNothing().when(personRepository).deleteById(num);

        assertAll(()->personServiceImplementation.deletePerson(num));

    }
        private List<Person> ourPersons () {
            List<Person> testPerson = new ArrayList<>();
            Person man1 = new Person();
            man1.setId(1);
            man1.setName("Ivan");
            Person man2 = new Person();
            man1.setId(2);
            man1.setName("Igor");
            Person man3 = new Person();
            man1.setId(3);
            man1.setName("Slava");
            Person women1 = new Person();
            man1.setId(10);
            man1.setName("Ira");
            Person woman2 = new Person();
            man1.setId(20);
            man1.setName("Sveta");
            Person woman3 = new Person();
            man1.setId(22);
            man1.setName("Olga");
            testPerson.add(man1);
            testPerson.add(man2);
            testPerson.add(man3);
            testPerson.add(women1);
            testPerson.add(woman2);
            testPerson.add(woman3);
            return testPerson;
        }
}