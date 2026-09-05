package com.gym.gym_ff_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GymFfBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				GymFfBackendApplication.class,
				args
		);
	}
}