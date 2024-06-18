package com.nikolay.dateApp;

import lombok.extern.log4j.Log4j2;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class DateAppApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(DateAppApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args)throws Exception{
		log.info("LOGGER CLASS NOW IS: {}", log.getClass());//смотрим какой ЛОГ активен

	}
}
