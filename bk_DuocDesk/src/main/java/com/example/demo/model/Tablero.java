package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set; // Importamos Set para las colecciones de relaciones

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TABLERO", schema = "ADMIN")
public class Tablero {

    @Id
    @Column(name = "ID_TABLERO")
    private int idTablero;

    @Column(name = "NOMBRE_TABLERO")
    private String nombreTablero;
    
    // --- Relaciones ---
    // Un Tablero puede tener muchas (Many) asignaciones de rol.
    @OneToMany(mappedBy = "tablero")
    private Set<UsuarioTableroRol> usuariosAsignados;

    // Un Tablero puede ser marcado como favorito muchas (Many) veces.
    @OneToMany(mappedBy = "tablero")
    private Set<Favorito> favoritos;

    // Un Tablero contiene muchas (Many) Listas.
    @OneToMany(mappedBy = "tablero")
    private Set<Lista> listas;

    // Un Tablero contiene muchas (Many) Bandejas.
    @OneToMany(mappedBy = "tablero")
    private Set<Bandeja> bandejas;
}