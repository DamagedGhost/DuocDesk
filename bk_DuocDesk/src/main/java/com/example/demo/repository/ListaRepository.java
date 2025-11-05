package com.example.demo.repository;

import com.example.demo.model.Lista;
import com.example.demo.model.ListaId; // Importar la clase ID
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//                       <Entidad, Tipo de la PK Compuesta>
public interface ListaRepository extends JpaRepository<Lista, ListaId> { // <-- CAMBIO: Long a ListaId
    // JpaRepository ya nos da findById(ListaId id), no necesitamos definirlo.
}