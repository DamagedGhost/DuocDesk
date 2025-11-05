package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
// @Embeddable: Indica que esta clase no es una entidad por sí misma,
// sino que será "incrustada" como parte de otra entidad (en este caso, como su PK).
@Embeddable
public class ListaId implements Serializable { // Debe implementar Serializable

    @Column(name = "ID_LISTA")
    private int idLista;

    // Esta es la parte de la PK que también es una FK a Tablero
    @Column(name = "TABLERO_ID_TABLERO")
    private int tableroId;
}