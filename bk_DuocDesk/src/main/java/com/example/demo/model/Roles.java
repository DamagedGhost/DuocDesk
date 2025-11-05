package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLES", schema = "ADMIN")
public class Roles {

    @Id
    @Column(name = "ID_ROL")
    private int idRol;

    @Column(name = "TIPO")
    private String tipo; // Ej: "Dueño", "Editor", "Invitado"

    // --- Relaciones ---
    // Un Rol puede estar asignado en muchas (Many) relaciones "UsuarioTableroRol".
    @OneToMany(mappedBy = "rol")
    private Set<UsuarioTableroRol> asignaciones;
}