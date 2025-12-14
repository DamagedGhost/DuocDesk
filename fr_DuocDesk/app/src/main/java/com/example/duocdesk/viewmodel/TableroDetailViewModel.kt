package com.example.duocdesk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.network.internal.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TableroDetailViewModel : ViewModel() {

    private val _tablero = MutableStateFlow<Tablero?>(null)
    val tablero = _tablero.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Cargar datos del tablero
    fun cargarTablero(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Asumimos que agregaste getTableroById en el backend o usamos el objeto pasado
                // Por ahora, si no tienes endpoint GET /:id, usaremos la lista o implementa GET /:id en node
                // Si el backend no tiene GET /:id, podemos confiar en que el objeto llegue por argumento,
                // pero para "refrescar" necesitamos el endpoint.
                // Usemos la llamada de invitar para actualizar por ahora si no hay GET individual.
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun crearLista(titulo: String) {
        val idTablero = _tablero.value?._id ?: return
        if (titulo.isBlank()) {
            _mensaje.value = "El título no puede estar vacío"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val body = mapOf("titulo" to titulo)
                val response = RetrofitInstance.api.crearLista(idTablero, body)

                if (response.isSuccessful && response.body() != null) {
                    _tablero.value = response.body()
                    _mensaje.value = "Lista '$titulo' creada"
                } else {
                    _mensaje.value = "Error al crear lista: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun eliminarLista(idLista: String) {
        val idTablero = _tablero.value?._id ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.eliminarLista(idTablero, idLista)

                if (response.isSuccessful && response.body() != null) {
                    _tablero.value = response.body()
                    _mensaje.value = "Lista eliminada"
                } else {
                    _mensaje.value = "Error al eliminar lista"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun crearTarjeta(idLista: String, titulo: String) {
        val idTablero = _tablero.value?._id ?: return
        if (titulo.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val body = mapOf("titulo" to titulo)
                val response = RetrofitInstance.api.crearTarjeta(idTablero, idLista, body)
                if (response.isSuccessful && response.body() != null) {
                    _tablero.value = response.body()
                }
            } catch (e: Exception) {
                _mensaje.value = "Error al crear tarjeta"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun eliminarTarjeta(idLista: String, idTarjeta: String) {
        val idTablero = _tablero.value?._id ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.eliminarTarjeta(idTablero, idLista, idTarjeta)
                if (response.isSuccessful && response.body() != null) {
                    _tablero.value = response.body()
                }
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar tarjeta"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método auxiliar para setear el tablero inicial desde la navegación
    fun setTableroInicial(t: Tablero) {
        _tablero.value = t
    }

    fun invitarMiembro(email: String) {
        val idTablero = _tablero.value?._id ?: return
        if (email.isBlank()) {
            _mensaje.value = "Ingresa un correo"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val body = mapOf("email" to email)
                val response = RetrofitInstance.api.invitarMiembro(idTablero, body)

                if (response.isSuccessful && response.body() != null) {
                    _tablero.value = response.body()
                    _mensaje.value = "Miembro agregado correctamente"
                } else {
                    _mensaje.value = "Error: Usuario no encontrado o ya registrado"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error de red: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun eliminarMiembro(idMiembro: String) {
        val idTablero = _tablero.value?._id ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.eliminarMiembro(idTablero, idMiembro)

                if (response.isSuccessful && response.body() != null) {
                    _tablero.value = response.body()
                    _mensaje.value = "Miembro eliminado."
                } else {
                    _mensaje.value = "Error al eliminar: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun eliminarTablero(onSuccess: () -> Unit) {
        val idTablero = _tablero.value?._id ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.eliminarTablero(idTablero)
                if (response.isSuccessful) {
                    onSuccess() // Navegar atrás
                } else {
                    _mensaje.value = "No se pudo eliminar el tablero"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limpiarMensaje() { _mensaje.value = null }
}