package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set; // Importamos Set para las colecciones de relaciones

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Le dice a JPA que esta clase es una entidad que debe mapear a una tabla
@Table(name = "USUARIO", schema = "ADMIN") // Vincula esta clase a la tabla exacta
public class Usuario {

    @Id // Marca este campo como la Clave Primaria (PK)
    @Column(name = "ID_USUARIO")
    private int idUsuario;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "EDAD")
    private int edad;

    @Column(name = "CARRERA")
    private String carrera;

    @Column(name = "CONTRASENA")
    private String contrasena;

    // --- Definición de Relaciones (el otro lado) ---

    // @OneToMany: Un Usuario puede tener muchas (Many) relaciones "UsuarioTableroRol".
    // "mappedBy = "usuario"": Le dice a JPA que la entidad "UsuarioTableroRol"
    // es la dueña de esta relación, en su campo llamado "usuario".

    // --- Relaciones ---
    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioTableroRol> rolesEnTableros; // <-- CAMBIO: UTR a UsuarioTableroRol

    @OneToMany(mappedBy = "usuario")
    private Set<Favorito> favoritos;
}