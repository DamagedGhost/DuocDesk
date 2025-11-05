package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
// TERCER INTENTO:
// Dejar el nombre tal cual como en el DDL, en MAYÚSCULAS y sin comillas.
// Oracle debería encontrarlo si lo almacenó como USUARIO-TABLERO-ROL.
@Table(name = "USUARIO-TABLERO-ROL", schema = "ADMIN")
public class UsuarioTableroRol {

    @Id
    @Column(name = "ID_UTR")
    private int idUtr;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "TABLERO_ID_TABLERO", referencedColumnName = "ID_TABLERO")
    private Tablero tablero;

    @ManyToOne
    @JoinColumn(name = "ROLES_ID_ROL", referencedColumnName = "ID_ROL")
    private Roles rol;
}