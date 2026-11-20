package com.campusfix.CampusFix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CampusFixApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusFixApplication.class, args);
	}

}
