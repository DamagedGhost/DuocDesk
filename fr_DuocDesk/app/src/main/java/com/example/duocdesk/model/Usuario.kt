package com.example.duocdesk.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// 1. @Entity: Le dice a Room que esta clase es una tabla en la base de datos.
// 2. indices: Le decimos que el 'correo' debe ser único. Si alguien intenta
//    registrarse con un email que ya existe, la base de datos dará un error.
@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["correo"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) // Room generará el ID automáticamente
    val id: Int = 0,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String // ¡Campo clave que faltaba!
    // Tu modelo 'Usuario.kt' original no tenía contraseña.
)