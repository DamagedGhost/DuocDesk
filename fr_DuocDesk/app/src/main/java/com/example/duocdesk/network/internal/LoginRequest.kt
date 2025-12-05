package com.example.duocdesk.network.internal

data class LoginRequest(
    val email: String,
    val password: String
)
// Agrega esta clase de datos para la respuesta de subida
data class UploadResponse(
    val mensaje: String,
    val fotoId: String,
    val usuario: com.example.duocdesk.model.Usuario // Reutilizamos tu modelo Usuario
)