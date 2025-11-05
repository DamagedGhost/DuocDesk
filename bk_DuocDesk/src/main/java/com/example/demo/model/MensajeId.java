package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

// 1. Esta es la clase para la PK Compuesta de Mensaje
// (Es idéntica en lógica a TarjetaId)
@Data
@Embeddable
public class MensajeId implements Serializable {

    @Column(name = "ID_MENSAJE")
    private int idMensaje;

    // 2. Hereda la PK Compuesta COMPLETA de su padre (Bandeja)
    private BandejaId bandejaId; 
}