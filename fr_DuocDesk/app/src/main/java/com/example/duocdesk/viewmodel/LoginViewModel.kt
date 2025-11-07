package com.example.duocdesk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.data.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Usamos AndroidViewModel otra vez
class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val usuarioDao = AppDatabase.getDatabase(app).usuarioDao()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // --- EVENTOS ---
    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(
            email = valor,
            errores = it.errores.copy(email = null, general = null)
        )}
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(
            password = valor,
            errores = it.errores.copy(password = null, general = null)
        )}
    }

    // --- LÓGICA PRINCIPAL ---
    fun onLoginClick() {
        if (validarFormulario()) {
            _uiState.update { it.copy(isLoading = true) }

            viewModelScope.launch {
                // 2. BUSCAMOS EN ROOM (Req #5)
                val usuario = usuarioDao.validarLogin(
                    correo = _uiState.value.email,
                    contrasena = _uiState.value.password
                )

                if (usuario != null) {
                    // 3. ¡ÉXITO! Usuario encontrado (Req #1)
                    _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                } else {
                    // 4. ERROR. Usuario no encontrado
                    _uiState.update { it.copy(
                        isLoading = false,
                        errores = it.errores.copy(general = "Correo o contraseña incorrectos")
                    )}
                }
            }
        }
    }

    // --- LÓGICA DE VALIDACIÓN (Privada) ---
    private fun validarFormulario(): Boolean {
        val estadoActual = _uiState.value
        val nuevosErrores = LoginErrores(
            email = if (estadoActual.email.isBlank()) "Email no puede estar vacío" else null,
            password = if (estadoActual.password.isBlank()) "Contraseña no puede estar vacía" else null
        )

        _uiState.update { it.copy(errores = nuevosErrores) }

        return nuevosErrores.email == null &&
                nuevosErrores.password == null &&
                nuevosErrores.general == null
    }
}