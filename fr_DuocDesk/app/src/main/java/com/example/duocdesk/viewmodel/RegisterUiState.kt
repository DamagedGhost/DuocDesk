package com.example.duocdesk.viewmodel

data class RegisterUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val carrera: String = "",
    val edad: String = "",
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false,
    val errores: RegisterErrores = RegisterErrores()
)

data class RegisterErrores(
    val nombre: String? = null,
    val apellido: String? = null,
    val email: String? = null,
    val password: String? = null,
    val carrera: String? = null,
    val edad: String? = null,
    val general: String? = null
)
