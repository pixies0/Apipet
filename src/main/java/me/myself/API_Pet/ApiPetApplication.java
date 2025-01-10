package me.myself.API_Pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "me.myself.API_Pet")
@EnableTransactionManagement
public class ApiPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPetApplication.class, args);
	}

}
