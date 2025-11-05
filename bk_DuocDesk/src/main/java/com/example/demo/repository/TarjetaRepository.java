package com.example.demo.repository;

import com.example.demo.model.Tarjeta;
import com.example.demo.model.TarjetaId; // Importar la clase ID
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//                         <Entidad, Tipo de la PK Compuesta>
public interface TarjetaRepository extends JpaRepository<Tarjeta, TarjetaId> { // <-- CAMBIO: Integer a TarjetaId

}