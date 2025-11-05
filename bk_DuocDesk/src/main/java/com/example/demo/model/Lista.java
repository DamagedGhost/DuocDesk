package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "LISTA", schema = "ADMIN")
public class Lista {

    // @EmbeddedId: Le dice a JPA que la Clave Primaria de esta entidad
    // es una clase "Embeddable", en este caso, ListaId.
    @EmbeddedId
    private ListaId id;

    // --- Esta es la magia de la Relación Identificativa ---
    // @MapsId("tableroId"): Le dice a JPA: "Oye, el campo 'tableroId' DENTRO de mi @EmbeddedId (ListaId)...
    // ...en realidad es una relación @ManyToOne con la entidad Tablero".
    // JPA usará esta pista para mapear la FK.
    @ManyToOne
    @MapsId("tableroId") // Debe coincidir con el nombre del campo en ListaId.java
    @JoinColumn(name = "TABLERO_ID_TABLERO") // La columna FK en la BD
    private Tablero tablero;

    // --- Relaciones (el otro lado) ---
    // Una Lista contiene muchas (Many) Tarjetas.
    @OneToMany(mappedBy = "lista")
    private Set<Tarjeta> tarjetas;

    public Lista orElse(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }
}