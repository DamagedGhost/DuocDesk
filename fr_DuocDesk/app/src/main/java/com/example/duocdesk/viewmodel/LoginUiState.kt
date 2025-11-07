package com.example.duocdesk.viewmodel

// 1. ESTADO DE LOS ERRORES
data class LoginErrores(
    val email: String? = null,
    val password: String? = null,
    val general: String? = null // Para errores como "Credenciales incorrectas"
)

// 2. ESTADO DE LA VISTA
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false, // <-- Para la animación (Req #1)
    val errores: LoginErrores = LoginErrores()
)