package com.backend.hl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
public class HabitLineApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		// Set environment variables from .env file
		System.setProperty("JWL_SECRET", dotenv.get("JWL_SECRET"));
		System.setProperty("POSTGRES_USERNAME", dotenv.get("POSTGRES_USERNAME"));
		System.setProperty("POSTGRES_PASSWORD", dotenv.get("POSTGRES_PASSWORD"));

		SpringApplication.run(HabitLineApplication.class, args);
	}

}
