package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

// Importamos tu repositorio para hacer una prueba
import com.example.demo.repository.UsuarioRepository; 

@SpringBootApplication
public class DuocDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuocDeskApplication.class, args);
		// Quitamos los System.out.println de aquí, porque se ejecutan muy pronto.
	}

	/**
	 * Este método se ejecutará automáticamente DESPUÉS de que Spring
	 * haya cargado todo y establecido la conexión a la BD.
	 */
	@Bean
	public CommandLineRunner checkDatabaseConnection(@Autowired UsuarioRepository usuarioRepository) {
		return args -> {
			try {
				// Hacemos la consulta más simple posible: contar cuántos usuarios hay.
				// Si esto funciona, la conexión es 100% exitosa.
				long userCount = usuarioRepository.count();
				
				System.out.println("\n==================================================");
				System.out.println("✅ CONEXIÓN A ORACLE DATABASE EXITOSA.");
				System.out.println("   Total de usuarios en la BD: " + userCount);
				System.out.println("   Servicio iniciado correctamente.");
				System.out.println("   Esperando solicitudes en http://localhost:8080");
				System.out.println("==================================================\n");
				System.out.println("Bienvenido a DuocDesk Application!");
			} catch (Exception e) {
				// Si falla, la aplicación se detendrá y verás este error.
				System.err.println("\n==================================================");
				System.err.println("❌ ERROR AL CONECTAR CON ORACLE DATABASE:");
				System.err.println(e.getMessage());
				System.err.println("==================================================\n");
			}
		};
	}
}