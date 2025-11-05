package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOG_DB", schema = "ADMIN")
public class LogDb {

    @Id
    @Column(name = "ID_LOG")
    private int idLog;

    // Tu DDL dice "NUMBER USUARIO", no es una FK.
    // Por lo tanto, lo mapeamos como un simple número.
    @Column(name = "USUARIO")
    private int idUsuario;

    @Column(name = "FECHA")
    private Timestamp fecha;

    @Column(name = "ACCION")
    private String accion;
}