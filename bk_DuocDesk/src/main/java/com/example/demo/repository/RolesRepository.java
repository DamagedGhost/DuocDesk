package com.example.demo.repository;

import com.example.demo.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar Optional

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    
    // MÉTODO AÑADIDO: Spring Data JPA creará la consulta automáticamente
    Optional<Roles> findByTipo(String tipo); 
}