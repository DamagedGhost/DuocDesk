package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.network.internal.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.duocdesk.network.internal.ApiService


class LoginViewModel(
    private val api: ApiService = RetrofitInstance.api
) : ViewModel() {



    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(email = valor, errores = it.errores.copy(email = null)) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, errores = it.errores.copy(password = null)) }
    }

    fun onLoginClick() {
        val estado = _uiState.value

        if (estado.email.isBlank() || !estado.email.contains("@")) {
            _uiState.update { it.copy(errores = it.errores.copy(email = "Correo inválido")) }
            return
        }

        if (estado.password.isBlank()) {
            _uiState.update { it.copy(errores = it.errores.copy(password = "Debe ingresar contraseña")) }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val body = mapOf("email" to estado.email, "password" to estado.password)
                val response = api.login(body) // ⭐ ahora sí usa el mock

                if (response.isSuccessful && response.body() != null) {
                    _uiState.update {
                        it.copy(
                            loginSuccess = true,
                            usuario = response.body(),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errores = it.errores.copy(general = "Credenciales incorrectas")
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errores = it.errores.copy(general = "Error de red")
                    )
                }
            }
        }
    }
}

