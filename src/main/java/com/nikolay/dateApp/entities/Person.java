package com.nikolay.dateApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
//import java.sql.Date;
import java.util.Date;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "person")
public class Person implements Serializable {

    @Id
  // @GeneratedValue(strategy= GenerationType.IDENTITY)//!!! нужно будет закоментрировать для объединения User и Person
    private Integer id;

    @NotBlank(message = "field Name must be filled in")
    @Size(min = 2, message = "Name must be at least 5 characters long")
    private String name;

    @NotBlank(message = "field Surname must be filled in")
    @Size(min = 2, message = "Name must be at least 5 characters long")
    private String surname;

    @NotNull(message = "pick your real sex")
    private String sex;

    @Column(name = "years_old")
    private long age;

    private String country;

    @NotBlank(message = "field city must be filled in")
    private String city;

    @NotBlank(message = "field email must be filled in")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "email was entered wrong!")
    private String email;

    @Digits(integer = 3,fraction = 0, message = "your height in cm")
    private Integer height;

    @Digits(integer = 3,fraction = 0, message = "your height in cm")
    private Integer weight;

    private String aboutMe;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;

    private java.time.LocalDate dateOfRegistration=java.time.LocalDate.now();// дата регистрации, устанавливается текущая дата когда вносится в БД


/*
   @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)//!!! нужно будет закоментрировать для объединения User и Person
    @PrimaryKeyJoinColumn
    private Worksheet worksheet;

*/



     /* здесь пытаемся соеденить таблицу Юзер и Персон,
     чтобы у одного юзера была только одна Учетная запись
     !!!!(в дальнейшем person и worksheet объеденим в одну таблицу)!!!
    ||
    ||
    \/

*/
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_person_column")
    private User user;

}
