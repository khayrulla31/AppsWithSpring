package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.User;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.*;
import org.mapstruct.Mapper;
import lombok.Data;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
//import java.sql.Date;


@Data
public class PersonRequest implements Serializable {
    @NotBlank(message = "field Name must be filled in")
    @Size(min = 2, message = "Name must be at least 5 characters long")
    private String name;

    //@NotBlank(message = "field Surname must be filled in")
    @Size(min = 2, message = "Name must be at least 5 characters long")
    private String surname;

//    @NotBlank(message = "field Patronymic must be filled in")
//    private String patronymic;

    //@NotNull(message = "pick your real sex")
    private String sex;

    @NotBlank(message = "field email must be filled in")
    private String country;

    //@NotBlank(message = "field email must be filled in")
    private String city;

    @NotNull(message = "field Date must be filled in")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;

    @NotBlank(message = "field email must be filled in")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "email was entered wrong!")
    //@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$");
    private String email;

    //@NotNull
    @Digits(integer = 3,fraction = 0, message = "your height in cm")
    private Integer height;

    //@NotNull
    @Digits(integer = 3,fraction = 0, message = "your height in cm")
    private Integer weight;

    @NotBlank
    private String aboutMe;

}
