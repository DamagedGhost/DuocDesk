package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FAVORITO", schema = "ADMIN")
public class Favorito {

    @Id
    @Column(name = "ID_FAV")
    private int idFav;

    @Column(name = "FECHA")
    private Timestamp fecha;

    // --- Relaciones (Claves Foráneas) ---
    // Muchos favoritos pueden pertenecer a Un Usuario
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;

    // Muchos favoritos pueden apuntar a Un Tablero
    @ManyToOne
    @JoinColumn(name = "TABLERO_ID_TABLERO", referencedColumnName = "ID_TABLERO")
    private Tablero tablero;
}