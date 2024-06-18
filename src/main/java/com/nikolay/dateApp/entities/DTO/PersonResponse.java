package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class PersonResponse implements Serializable {
    private String name;
    private long age;
    private String country;
    private String aboutMe;

}
