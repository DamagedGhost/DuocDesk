package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class TarjetaId implements Serializable {

    @Column(name = "ID_TARJETA")
    private int idTarjeta;

    // --- Clave Compuesta Anidada ---
    // En lugar de declarar "idLista" y "tableroId" otra vez,
    // simplemente re-usamos la clave compuesta del padre (ListaId).
    // JPA entenderá que esta PK está formada por 3 columnas.
    private ListaId listaId; 
}