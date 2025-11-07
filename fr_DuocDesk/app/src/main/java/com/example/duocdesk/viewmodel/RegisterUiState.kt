package com.example.duocdesk.viewmodel

// 1. ESTADO DE LOS ERRORES (Como en tu guía)
// Guardará los mensajes de error para cada campo.
data class RegisterErrores(
    val nombre: String? = null,
    val apellido: String? = null,
    val email: String? = null,
    val password: String? = null
)

// 2. ESTADO DE LA VISTA (Como en tu guía)
// Guarda todos los datos del formulario Y el objeto de errores.
data class RegisterUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false, // <-- Para la animación (Req #1)
    val errores: RegisterErrores = RegisterErrores() // <-- Objeto anidado
)