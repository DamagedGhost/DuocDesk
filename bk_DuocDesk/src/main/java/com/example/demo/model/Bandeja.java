package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "BANDEJA", schema = "ADMIN")
public class Bandeja {

    // 2. Usamos la clave compuesta
    @EmbeddedId
    private BandejaId id;

    @Column(name = "FECHA")
    private Timestamp fecha;

    // 3. Mapeamos la parte "tableroId" de nuestra clave compuesta
    // a la entidad real de Tablero (Idéntico a como lo hizo Lista).
    @ManyToOne
    @MapsId("tableroId") // Mapea "tableroId" de la clase BandejaId
    @JoinColumn(name = "TABLERO_ID_TABLERO")
    private Tablero tablero;

    // --- Relaciones ---
    // Una Bandeja contiene muchos Mensajes
    @OneToMany(mappedBy = "bandeja")
    private Set<Mensaje> mensajes;
}