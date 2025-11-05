package com.example.duocdesk.model

data class Usuario(
    val idUsuario: Int,
    val correo: String?,
    val nombre: String?,
    val apellido: String?,
    val carrera: String?
    // Quitamos 'edad' y 'contrasena' porque el DTO no los incluye
)