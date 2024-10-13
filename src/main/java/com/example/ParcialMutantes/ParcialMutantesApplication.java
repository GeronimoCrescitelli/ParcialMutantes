package com.example.ParcialMutantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.ParcialMutantes.services.AdnService.isMutant;


@SpringBootApplication
public class ParcialMutantesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ParcialMutantesApplication.class, args);
		System.out.println("Corriendo aplicación");

	}
}
