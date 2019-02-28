package com.example.RESTexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication meta-annotation that pulls
// in component scanning, autoconfiguration, and property support
@SpringBootApplication
public class ResTexampleApplication {

	public static void main(String[] args) {

		SpringApplication.run(ResTexampleApplication.class, args);
	}

}
