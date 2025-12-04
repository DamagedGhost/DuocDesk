package com.example.duocdesk.viewmodel

import com.example.duocdesk.model.Usuario

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val usuario: Usuario? = null,
    val errores: LoginErrores = LoginErrores()
)

data class LoginErrores(
    val email: String? = null,
    val password: String? = null,
    val general: String? = null
)
