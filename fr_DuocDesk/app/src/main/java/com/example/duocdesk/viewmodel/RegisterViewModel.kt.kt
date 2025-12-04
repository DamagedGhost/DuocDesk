package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.internal.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.duocdesk.network.internal.ApiService


class RegisterViewModel(
    private val api: ApiService = RetrofitInstance.api
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onNombreChange(valor: String) {
        _uiState.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onApellidoChange(valor: String) {
        _uiState.update { it.copy(apellido = valor, errores = it.errores.copy(apellido = null)) }
    }

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(email = valor, errores = it.errores.copy(email = null)) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, errores = it.errores.copy(password = null)) }
    }

    fun registrar(usuario: Usuario, validar: Boolean = true) {
        if (validar && !validarFormulario()) return

        _uiState.update { it.copy(isLoading = true, errores = it.errores.copy(general = null)) }

        viewModelScope.launch {
            try {
                val response = api.registrar(usuario)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationSuccess = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errores = it.errores.copy(
                                general = "No se pudo registrar. Código: ${response.code()}"
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errores = it.errores.copy(general = "Error de red: ${e.message}")
                    )
                }
            }
        }
    }


    private fun validarFormulario(): Boolean {
        val estado = _uiState.value
        val errores = RegisterErrores(
            nombre = if (estado.nombre.isBlank()) "El nombre es obligatorio" else null,
            apellido = if (estado.apellido.isBlank()) "El apellido es obligatorio" else null,
            email = if (!estado.email.contains("@")) "Correo inválido" else null,
            password = if (estado.password.length < 4) "Mínimo 4 caracteres" else null
        )

        _uiState.update { it.copy(errores = errores) }

        return errores.nombre == null &&
                errores.apellido == null &&
                errores.email == null &&
                errores.password == null
    }
}
