package me.myself.API_Pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "me.myself.API_Pet.Controller")
public class ApiPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPetApplication.class, args);
	}

}
