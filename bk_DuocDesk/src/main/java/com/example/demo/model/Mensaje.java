package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "MENSAJE", schema = "ADMIN")
public class Mensaje {

    // 3. Usamos la clave compuesta del "nieto"
    @EmbeddedId
    private MensajeId id;

    // 4. Mapeamos la parte "bandejaId" de nuestra clave compuesta
    // a la entidad real de Bandeja (Idéntico a como lo hizo Tarjeta).
    @ManyToOne
    @MapsId("bandejaId") // Mapea "bandejaId" de la clase MensajeId
    @JoinColumns({
        @JoinColumn(name = "BANDEJA_ID_BANDEJA", referencedColumnName = "ID_BANDEJA"),
        @JoinColumn(name = "BANDEJA_ID_TABLERO", referencedColumnName = "TABLERO_ID_TABLERO")
    })
    private Bandeja bandeja;

    // --- El resto de las columnas ---
    
    @Column(name = "ACCION")
    private String accion;

    @Column(name = "MENSAJE")
    private String mensaje;

    @Column(name = "FECHA")
    private Timestamp fecha;
}