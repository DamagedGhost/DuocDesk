package com.example.demo.repository;

import com.example.demo.model.UsuarioTableroRol; // <-- CAMBIO: UTR a UsuarioTableroRol
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//                  <Entidad, Tipo de la PK>
public interface UsuarioTableroRolRepository extends JpaRepository<UsuarioTableroRol, Integer> { // <-- CAMBIO: UTR a UsuarioTableroRol
    // JpaRepository ya nos da .save(), .findById(), .findAll(), etc.
}