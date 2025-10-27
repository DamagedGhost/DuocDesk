package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // <-- CAMBIO: De @MappedSuperclass a @Entity
@Table(name = "USUARIO", schema = "ADMIN") // <-- AÑADIDO: Para apuntar a tu tabla
public class Usuario {

    @Column(name = "CORREO")
    private String CORREO;

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) <-- ELIMINADO: Tu DDL no muestra auto-incremento.
    // El ID se deberá proveer manualmente al crear un usuario.
    @Column(name = "ID_USUARIO")
    private int ID_USUARIO; // O usa Long si el número puede ser muy grande

    @Column(name = "NOMBRE")
    private String NOMBRE;

    @Column(name = "APELLIDO")
    private String APELLIDO;

    @Column(name = "EDAD")
    private int EDAD;

    @Column(name = "CARRERA")
    private String CARRERA;

    @Column(name = "CONTRASENA")
    private String CONTRASENA;
}