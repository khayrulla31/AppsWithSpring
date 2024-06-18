package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.Person;
import com.nikolay.dateApp.entities.Role;
import com.nikolay.dateApp.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-18T14:46:45+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
public class PersonMapperImpl implements PersonMapper {

    @Override
    public UserResponse fromUserToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setUserName( user.getUserName() );
        userResponse.setRoles( roleListToRoleResponseList( user.getRoles() ) );

        return userResponse;
    }

    @Override
    public RoleResponse fromRoleToResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setName( role.getName() );

        return roleResponse;
    }

    @Override
    public PersonResponse fromPersonEntityToPersonResponse(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonResponse personResponse = new PersonResponse();

        personResponse.setName( person.getName() );
        personResponse.setAge( person.getAge() );
        personResponse.setCountry( person.getCountry() );
        personResponse.setAboutMe( person.getAboutMe() );

        return personResponse;
    }

    @Override
    public Person fromPersonRequestToPerson(PersonRequest personRequest) {
        if ( personRequest == null ) {
            return null;
        }

        Person person = new Person();

        person.setName( personRequest.getName() );
        person.setSurname( personRequest.getSurname() );
        person.setSex( personRequest.getSex() );
        person.setCountry( personRequest.getCountry() );
        person.setCity( personRequest.getCity() );
        person.setEmail( personRequest.getEmail() );
        person.setHeight( personRequest.getHeight() );
        person.setWeight( personRequest.getWeight() );
        person.setAboutMe( personRequest.getAboutMe() );
        person.setDateOfBirth( personRequest.getDateOfBirth() );

        return person;
    }

    @Override
    public PersonRequest fromPersonToPersonRequest(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonRequest personRequest = new PersonRequest();

        personRequest.setName( person.getName() );
        personRequest.setSurname( person.getSurname() );
        personRequest.setSex( person.getSex() );
        personRequest.setCountry( person.getCountry() );
        personRequest.setCity( person.getCity() );
        personRequest.setDateOfBirth( person.getDateOfBirth() );
        personRequest.setEmail( person.getEmail() );
        personRequest.setHeight( person.getHeight() );
        personRequest.setWeight( person.getWeight() );
        personRequest.setAboutMe( person.getAboutMe() );

        return personRequest;
    }

    protected List<RoleResponse> roleListToRoleResponseList(List<Role> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleResponse> list1 = new ArrayList<RoleResponse>( list.size() );
        for ( Role role : list ) {
            list1.add( fromRoleToResponse( role ) );
        }

        return list1;
    }
}
