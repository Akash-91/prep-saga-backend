package com.prep_saga.PrepSaga;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrepSagaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrepSagaApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println(">>> JWT_SECRET from env: " + System.getenv("JWT_SECRET"));
		System.out.println(">>> Active profile: " + System.getProperty("spring.profiles.active"));
	}


	@PostConstruct
	public void logEnvironment() {
		System.out.println("ACTIVE PROFILE: " + System.getProperty("spring.profiles.active"));
		System.out.println("JWT_SECRET: " + System.getenv("JWT_SECRET"));
		System.out.println("DB_HOST: " + System.getenv("DB_HOST"));
	}
}
