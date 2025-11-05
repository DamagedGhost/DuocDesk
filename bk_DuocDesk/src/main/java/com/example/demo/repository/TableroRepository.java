package com.example.demo.repository;

import com.example.demo.model.Tablero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableroRepository extends JpaRepository<Tablero, Integer>{

}
