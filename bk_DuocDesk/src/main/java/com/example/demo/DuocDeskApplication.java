package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DuocDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuocDeskApplication.class, args);

		System.out.println("Servicio iniciado correctamente");
		System.out.println("Esperando solicitudes...");
	}
}
