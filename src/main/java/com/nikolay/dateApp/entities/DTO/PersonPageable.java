package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.Person;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class PersonPageable implements Serializable {
    private List<PersonResponse> listOfPerson;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
