package com.example.duocdesk.model

data class Usuario(
    val _id: String? = null,
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val carrera: String = "",
    val edad: Int = 0,
    val rolGlobal: String = "USER"
)


