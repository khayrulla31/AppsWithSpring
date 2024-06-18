package com.nikolay.dateApp.exeptions;

public class PersonNotFoundExeption extends RuntimeException{
    private static final long serialVerisionUID = 1;
    public PersonNotFoundExeption(String message) {
        super(message);
    }
}
