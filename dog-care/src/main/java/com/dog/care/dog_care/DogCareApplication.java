package com.dog.care.dog_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.TimeZone;

@SpringBootApplication
@CrossOrigin
@EnableScheduling
public class DogCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogCareApplication.class, args);

	}




}
