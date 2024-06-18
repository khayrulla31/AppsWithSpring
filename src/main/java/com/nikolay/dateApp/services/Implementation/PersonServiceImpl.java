package com.nikolay.dateApp.services.Implementation;

import com.nikolay.dateApp.entities.DTO.PersonMapper;
import com.nikolay.dateApp.entities.DTO.PersonPageable;
import com.nikolay.dateApp.entities.DTO.PersonResponse;
import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.entities.User;
import com.nikolay.dateApp.exeptions.PersonNotFoundExeption;
import com.nikolay.dateApp.repositories.PersonRepository;
import com.nikolay.dateApp.repositories.UserRepository;
import com.nikolay.dateApp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private UserRepository userRepository;//добавили чтобы попытаться связать user и person

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PersonPageable findAll(Pageable pageable) {
        Page<Person>personPage=personRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        return convert(personPage);
    }

    @Override
    public PersonPageable findAllByCounty(Pageable pageable, String str) {
        Page<Person>people = personRepository.findAllByCountryIgnoreCase(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),str);
        return convert(people);
    }
    @Override
    public PersonPageable findAllByName(Pageable pageable, String name) {
        Page<Person> personPage = personRepository.findAllByNameIgnoreCase(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), name);

        return convert(personPage);
    }


    @Override
    public PersonPageable findAllASC(Pageable pageable) {
       Page<Person>personPage = personRepository.findAllByOrderByAgeAsc(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return  convert(personPage);
    }

    @Override
    public PersonPageable findAllDESC(Pageable pageable) {
        Page<Person>personPage = personRepository.findAllByOrderByAgeDesc(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return convert(personPage);
    }

    @Override
    public PersonResponse findById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(()->new PersonNotFoundExeption("ЧЕЛОВЕК НЕ НАЙДЕН!!!"));
        return PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(person);
    }

    @Override
    public PersonResponse savePerson(Person person, Integer userID) {
        Person newPerson=new Person();

//        newPerson.setName(person.getName());
//        newPerson.setAge(fullYearsOld(person.getDateOfBirth()));
//        newPerson.setCountry(person.getCountry());
//        newPerson.setAboutMe(person.getAboutMe());
//        newPerson.setDateOfBirth(person.getDateOfBirth());
        newPerson.setName(person.getName());
        newPerson.setSurname(person.getSurname());
        newPerson.setSex(person.getSex());
        newPerson.setAge(fullYearsOld(person.getDateOfBirth()));//количество полных лет на данный момент
        newPerson.setCountry(person.getCountry());
        newPerson.setCity(person.getCity());
        newPerson.setDateOfBirth(new Date(person.getDateOfBirth().getTime()) );//из util.DAta преображаем в sql.Data
        newPerson.setEmail(person.getEmail());
        newPerson.setHeight(person.getHeight());
        newPerson.setWeight(person.getWeight());
        newPerson.setAboutMe(person.getAboutMe());

        User user = userRepository.findById(userID).orElseThrow(()->new PersonNotFoundExeption("USER DOESN'T EXIST"));
        newPerson.setUser(user);

        Person person1 = personRepository.save(newPerson);

        return PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(person1);
    }

    @Override
    public PersonResponse updatePerson(Integer id,Person person) {
        Optional<Person> checkIfExist=personRepository.findById(id);
            if (checkIfExist.isEmpty()){
                throw new PersonNotFoundExeption("НЕТУ ТАКОГО ЧЕЛОВЕКА!!!");
            }
            Person newPerson=checkIfExist.get();

        if (person.getName()!=null){
            newPerson.setName(person.getName());//проверяем что введено в запросе и это меняем
        }if (person.getName()!=null){
            newPerson.setName(person.getName());//проверяем что введено в запросе и это меняем
        }if (person.getAge()!=0){
            newPerson.setAge(person.getAge());
        }if (person.getAboutMe()!=null){
            newPerson.setAboutMe(person.getAboutMe());//проверяем что введено в запросе и это меняем
        }if (person.getDateOfBirth()!=null){
            newPerson.setDateOfBirth(person.getDateOfBirth());
        }

        Person person1 = personRepository.save(newPerson);

        return PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(person1);
    }

    @Override
    public void deletePerson(Integer id) {
        Person person=personRepository.findById(id).orElseThrow(()->new PersonNotFoundExeption("НЕТУ ТАКОГО ЧЕЛОВЕКА!!!"));
        personRepository.delete(person);

    }

    private static long fullYearsOld(java.util.Date date) {
        Instant nows = Instant.now();//текущее время/дата
        Instant ago = Instant.ofEpochMilli(date.getTime());//дата рождения
        return ChronoUnit.YEARS.between(ago.atZone(ZoneId.systemDefault()), nows.atZone(ZoneId.systemDefault()));
    }

    private PersonPageable convert(Page<Person> listPer){
        PersonPageable personPageable = new PersonPageable();
        personPageable.setListOfPerson(listPer.stream().map(p->PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(p)).collect(Collectors.toList()));
        personPageable.setPageNo(listPer.getNumber());
        personPageable.setPageSize(listPer.getSize());
        personPageable.setLast(listPer.isLast());
        personPageable.setTotalPages(listPer.getTotalPages());
        personPageable.setTotalElements(listPer.getTotalElements());

        return personPageable;
    }
}
