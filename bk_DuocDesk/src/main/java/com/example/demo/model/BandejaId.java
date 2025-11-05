package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

// 1. Esta es la clase para la PK Compuesta de Bandeja
// (Es idéntica en lógica a ListaId)
@Data
@Embeddable
public class BandejaId implements Serializable {

    @Column(name = "ID_BANDEJA")
    private int idBandeja;

    @Column(name = "TABLERO_ID_TABLERO")
    private int tableroId;
}