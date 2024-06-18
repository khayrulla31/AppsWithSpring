package com.nikolay.dateApp.entities.DTO;


import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.entities.Role;
import com.nikolay.dateApp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper PERSON_MAPPER= Mappers.getMapper(PersonMapper.class);
    UserResponse fromUserToResponse(User user);

    RoleResponse fromRoleToResponse(Role role);

    PersonResponse fromPersonEntityToPersonResponse(Person person);
    Person fromPersonRequestToPerson(PersonRequest personRequest);
    PersonRequest fromPersonToPersonRequest(Person person);









}
