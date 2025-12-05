package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.internal.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilEditViewModel : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(UserSession.currentUser)
    val usuario = _usuario.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    // Mantener cambios antes de guardar
    private var usuarioEditado: Usuario = UserSession.currentUser ?: Usuario()

    fun actualizarCampo(
        nombre: String? = null,
        apellido: String? = null,
        carrera: String? = null,
        edad: Int? = null
    ) {
        usuarioEditado = usuarioEditado.copy(
            nombre = nombre ?: usuarioEditado.nombre,
            apellido = apellido ?: usuarioEditado.apellido,
            carrera = carrera ?: usuarioEditado.carrera,
            edad = edad ?: usuarioEditado.edad
        )
    }

    fun guardarCambios() {
        val id = usuarioEditado._id ?: run {
            _mensaje.value = "Error: ID de usuario nulo"
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.actualizarPerfil(id, usuarioEditado)

                if (response.isSuccessful && response.body() != null) {
                    // Backend devuelve: { "usuario": "{json-del-usuario}" }
                    val respMap = response.body()!!        // Map<String, String>
                    val usuarioJson = respMap["usuario"]

                    if (usuarioJson != null) {
                        val usuarioActualizado =
                            Gson().fromJson(usuarioJson, Usuario::class.java)

                        UserSession.currentUser = usuarioActualizado
                        _usuario.value = usuarioActualizado
                        _mensaje.value = "Cambios guardados correctamente"
                    } else {
                        _mensaje.value = "Error: backend no envió 'usuario'"
                    }

                } else {
                    val errorTexto = response.errorBody()?.string()
                    _mensaje.value =
                        "Error guardando cambios (${response.code()}): ${errorTexto ?: "desconocido"}"
                }
            } catch (e: Exception) {
                _mensaje.value = "Excepción guardando cambios: ${e.message}"
            }
        }
    }

    fun eliminarCuenta(onSuccess: () -> Unit) {
        val id = usuarioEditado._id ?: run {
            _mensaje.value = "Error: ID de usuario nulo"
            return
        }

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.eliminarUsuario(id)

                if (response.isSuccessful) {
                    // No necesitamos el cuerpo, con que sea 2xx está ok
                    UserSession.currentUser = null
                    _mensaje.value = "Cuenta eliminada correctamente"
                    onSuccess()
                } else {
                    val errorTexto = response.errorBody()?.string()
                    _mensaje.value =
                        "Error eliminando cuenta (${response.code()}): ${errorTexto ?: "desconocido"}"
                }

            } catch (e: Exception) {
                _mensaje.value = "Excepción eliminando cuenta: ${e.message}"
            }
        }
    }
}
