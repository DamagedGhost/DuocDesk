package com.example.duocdesk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.data.AppDatabase
import com.example.duocdesk.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Usamos AndroidViewModel para poder acceder al 'Context' de la aplicación
// y así poder instanciar la base de datos de Room.
class RegisterViewModel(app: Application) : AndroidViewModel(app) {

    // 2. Obtenemos la instancia del DAO de Room
    private val usuarioDao = AppDatabase.getDatabase(app).usuarioDao()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    // --- EVENTOS (Llamados desde la Vista) ---

    fun onNombreChange(valor: String) {
        // 3. Actualiza el estado Y limpia el error de ese campo (como en tu guía)
        _uiState.update { it.copy(
            nombre = valor,
            errores = it.errores.copy(nombre = null)
        )}
    }

    fun onApellidoChange(valor: String) {
        _uiState.update { it.copy(
            apellido = valor,
            errores = it.errores.copy(apellido = null)
        )}
    }

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(
            email = valor,
            errores = it.errores.copy(email = null)
        )}
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(
            password = valor,
            errores = it.errores.copy(password = null)
        )}
    }

    // --- LÓGICA PRINCIPAL ---

    fun onRegisterClick() {
        if (validarFormulario()) {
            // Si las validaciones del formulario pasan, iniciamos el 'loading'
            // y lanzamos una corrutina para interactuar con la base de datos.
            _uiState.update { it.copy(isLoading = true) }

            viewModelScope.launch {
                try {
                    // 4. CREAMOS EL OBJETO USUARIO CON LOS DATOS DEL ESTADO
                    val nuevoUsuario = Usuario(
                        nombre = _uiState.value.nombre,
                        apellido = _uiState.value.apellido,
                        correo = _uiState.value.email,
                        contrasena = _uiState.value.password
                    )

                    // 5. INSERTAMOS EN ROOM (Req #5)
                    usuarioDao.insertarUsuario(nuevoUsuario)

                    // 6. ¡ÉXITO! Actualizamos el estado para mostrar la animación (Req #1)
                    _uiState.update { it.copy(isLoading = false, registrationSuccess = true) }

                } catch (e: Exception) {
                    // 7. Si falla (ej. email duplicado), lo mostramos en el campo email
                    _uiState.update { it.copy(
                        isLoading = false,
                        errores = it.errores.copy(email = "El correo ya está registrado")
                    )}
                }
            }
        }
    }

    // --- LÓGICA DE VALIDACIÓN (Privada) ---

    private fun validarFormulario(): Boolean {
        val estadoActual = _uiState.value

        // 8. Creamos el nuevo objeto de errores (como en tu guía)
        val nuevosErrores = RegisterErrores(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre es obligatorio" else null,
            apellido = if (estadoActual.apellido.isBlank()) "El apellido es obligatorio" else null,
            email = if (!estadoActual.email.contains("@")) "Correo inválido" else null,
            password = if (estadoActual.password.length < 6) "Mínimo 6 caracteres" else null
        )

        // 9. Actualizamos el estado con TODOS los errores nuevos
        _uiState.update { it.copy(errores = nuevosErrores) }

        // 10. Devolvemos true si no hay ningún error (todos los campos son null)
        return nuevosErrores.nombre == null &&
                nuevosErrores.apellido == null &&
                nuevosErrores.email == null &&
                nuevosErrores.password == null
    }
}