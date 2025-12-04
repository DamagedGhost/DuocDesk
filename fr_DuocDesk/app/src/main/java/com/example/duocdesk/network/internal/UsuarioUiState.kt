package com.example.duocdesk.network.internal
import com.example.duocdesk.model.Usuario

data class UsuarioUiState(
    val isLoading: Boolean = false,
    val usuario: Usuario? = null,
    val usuarios: List<Usuario> = emptyList(),
    val error: String? = null
)
