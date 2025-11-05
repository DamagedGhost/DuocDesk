package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "TARJETA", schema = "ADMIN")
public class Tarjeta {

    @EmbeddedId // La PK de Tarjeta es la clase TarjetaId
    private TarjetaId id;

    // --- Mapeo de la FK Compuesta Anidada ---
    // @MapsId("listaId"): Le dice a JPA: "El campo 'listaId' DENTRO de mi @EmbeddedId (TarjetaId)...
    // ...es una relación @ManyToOne con la entidad Lista".
    @ManyToOne
    @MapsId("listaId") // Coincide con el campo "listaId" en TarjetaId.java
    
    // @JoinColumns: Como la entidad "Lista" tiene una PK compuesta, debemos
    // decirle a JPA CÓMO unir CADA PARTE de esa clave compuesta.
    @JoinColumns({
        @JoinColumn(name = "LISTA_ID_LISTA", referencedColumnName = "ID_LISTA"),
        @JoinColumn(name = "LISTA_TABLERO_ID_TABLERO", referencedColumnName = "TABLERO_ID_TABLERO")
    })
    private Lista lista;

    // --- El resto de tus columnas ---
    
    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "TIPO_TARJETA")
    private String tipoTarjeta;

    @Column(name = "PRIORIDAD")
    private String prioridad;

    @Column(name = "COMENTARIOS")
    private String comentarios;

    @Column(name = "FECHA")
    private Timestamp fecha;
}