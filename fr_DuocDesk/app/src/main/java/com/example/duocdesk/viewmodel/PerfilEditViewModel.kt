package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.network.internal.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class PerfilEditViewModel(
    private val api: ApiService = ApiService.crear()
) : ViewModel() {

    private val _usuario = MutableStateFlow(UserSession.currentUser)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun actualizarCampo(
        nombre: String? = null,
        apellido: String? = null,
        carrera: String? = null,
        edad: Int? = null
    ) {
        val actual = _usuario.value ?: return

        _usuario.value = actual.copy(
            nombre = nombre ?: actual.nombre,
            apellido = apellido ?: actual.apellido,
            carrera = carrera ?: actual.carrera,
            edad = edad ?: actual.edad
        )
    }

    fun guardarCambios() {
        val user = _usuario.value ?: return

        viewModelScope.launch {
            try {
                val respuesta: Response<Usuario> =
                    api.actualizarPerfil(user._id!!, user)

                if (respuesta.isSuccessful) {
                    val actualizado = respuesta.body()
                    UserSession.currentUser = actualizado
                    _usuario.value = actualizado
                    _mensaje.value = "Perfil actualizado correctamente"
                } else {
                    _mensaje.value = "Error: ${respuesta.code()}"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión"
            }
        }
    }
}
