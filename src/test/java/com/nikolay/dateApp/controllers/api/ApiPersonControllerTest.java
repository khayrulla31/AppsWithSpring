package com.nikolay.dateApp.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.dateApp.entities.DTO.PersonMapper;
import com.nikolay.dateApp.entities.DTO.PersonResponse;
import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ApiPersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ApiPersonControllerTest {
    @MockBean
    PersonService personService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findAllPersonsTest() throws Exception {
//given
        //приводим Person к типу PersonResponse(потому что этот метод возвращает этот тип)
        //given(personService.findAllPerson()).willReturn(getListofPerson().stream().map(person -> PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(person)).collect(Collectors.toList()));
//when + then
        ResultActions resultActions = mockMvc.perform(get("/second/api/person")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(
                        "[{'name': 'ivan','surname': 'ivanov'},{'name': 'tanya','surname': 'petrova'},{'name': 'sergey','surname': 'zhukov'}]")
                );
        //Mockito.verify(personService,times(1)).findAllPerson();//проверяем сколько раз вызывается personService при выполнении метода


    }

    @Test
    void findPersonById_ReturnPersonResponse() throws Exception {
        Integer id=2;
//given
        //List<PersonResponse>listPersonresponse=getListofPerson().stream().map(person -> PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(person)).collect(Collectors.toList());
        //given(personService.findById(2)).willReturn(listPersonresponse.get(1));//!!!в List первый индекс == 0

//when + then
       ResultActions resultActions = mockMvc.perform(get("/second/api/person/{id}",id));

       resultActions.andExpect(status().isOk())
                   // .andExpect(jsonPath("name",is(listPersonresponse.get(1).getName())))//еще вот так можно проверить поля в JSON
                   // .andExpect(jsonPath("surname",is(listPersonresponse.get(1).getSurname())))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value("tanya"))//  еще вот так можно проверить JSON
                    .andExpect(jsonPath("$.surname").value("petrova"))
                    .andExpect(content().json("{'name': 'tanya','surname': 'petrova'}"));

       // assertEquals(personService.findById(2),listPersonresponse.get(1));
    }

    @Test
    void findByNames_ReturnPersonResponse() throws Exception{
        String name="sergey";
//given
        Person p1=new Person();
        p1.setName("sergey");
       // p1.setSurname("zhukov");
        List<Person>lser=new ArrayList<>();
        lser.add(p1);
        //given(personService.findByName("sergey")).willReturn(lser);//!!!в List первый индекс == 0
//when + then

        ResultActions resultActions = mockMvc.perform(get("/second/api/person/names/{name}",name));

        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                     .andExpect(content().json(
                        "[{'name': 'sergey','surname': 'zhukov'}]")
                );
    }

    @Test
    void savePersonI_ReturnCreated() throws Exception{
//given
        Person person=new Person();
        person.setName("ivan");
        //person.setSurname("ivanov");
//when
        //Mockito.when(personService.savePerson(Mockito.any(Person.class))).thenReturn(person);
//then
        ResultActions resultActions = mockMvc.perform(post("/second/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)));//конвертируем объект
//!!!!вверху запрос
                //!!!внизу ожидания после выполнения запроса
        resultActions.andExpect(status().isCreated())
                     .andExpect(jsonPath("$.name").value(person.getName()))
                     .andExpect(jsonPath("$.surname").value("ivanov"));

        //Mockito.verify(personService,times(1)).savePerson(person);//проверяем сколько раз вызывается personService при выполнении метода
    }

    @Test
    void alterPerson() throws Exception{
//given
        Person person=new Person();
        person.setName("Ilya");
       // person.setSurname("petrov");

//when
        Integer id=1;

        //when(personService.updatePerson(id, PersonMapper.PERSON_MAPPER.fromPersonToPersonRequest(person))).thenReturn(PersonMapper.PERSON_MAPPER.fromPersonEntityToPersonResponse(person));

//then
        ResultActions resultActions=mockMvc.perform(patch("/second/api/{id}",id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(person))
                    .accept(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk()).
                 andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name").value("Ilya"))
                .andExpect(jsonPath("$.surname").value("petrov")
                );

        //Mockito.verify(personService,Mockito.times(1)).updatePerson(id,PersonMapper.PERSON_MAPPER.fromPersonToPersonRequest(person));

    }

    @Test
    void deletePersonnn_ReturnSting() throws Exception{
        Integer id = 1;
        //given + when
        //doNothing().when(personService).deletePerson(getListofPerson().get(id).getId());
//then
        ResultActions resultActions = mockMvc.perform(delete("/second/api/{id}", id));

        //что ожидаем от результата выполнения
        resultActions.andExpect(status().isNoContent());

        Mockito.verify(personService,Mockito.times(1)).deletePerson(1);
    }
   // private List<Person> getListofPerson(){
//        List<Person> persons= List.of(
//                new Person(1,"ivan","ivanov", LocalDate.of(2000,12,12))
//                ,new Person(2,"tanya","petrova", LocalDate.of(1990,1,21))
//                ,new Person(3,"sergey","zhukov", LocalDate.of(19780,9,30))
//        );
   //     return persons;
   // }
}