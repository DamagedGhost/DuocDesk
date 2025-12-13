package com.example.duocdesk.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.network.internal.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.duocdesk.model.TableroRequest

class TableroViewModel : ViewModel() {

    private val _tableros = MutableStateFlow<List<Tablero>>(emptyList())
    val tableros = _tableros.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Para la búsqueda
    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda = _textoBusqueda.asStateFlow()

    init {
        cargarTableros()
    }

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    fun cargarTableros() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getTableros()
                if (response.isSuccessful) {
                    _tableros.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Función para el botón flotante de la biblioteca
    fun abrirReservaSala(context: Context) {
        val url = "https://agenda-bibliotecas.duoc.cl/reserve/spaces/sbernardo"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun onBusquedaChange(texto: String) {
        _textoBusqueda.value = texto
    }

    // Filtrar localmente por búsqueda
    fun obtenerTablerosFiltrados(): List<Tablero> {
        val query = _textoBusqueda.value.lowercase()
        return if (query.isEmpty()) {
            _tableros.value
        } else {
            _tableros.value.filter { it.nombre_tablero.lowercase().contains(query) }
        }
    }

    // Lógica simple de favoritos (en memoria por ahora)
    fun toggleFavorito(tableroId: String) {
        val listaActualizada = _tableros.value.map {
            if (it._id == tableroId) it.copy(esFavorito = !it.esFavorito) else it
        }
        _tableros.value = listaActualizada
    }

    fun crearTablero(nombre: String) {
        val currentUser = UserSession.currentUser

        if (currentUser?._id == null) {
            _mensaje.value = "Error: No hay sesión activa"
            return
        }

        if (nombre.isBlank()) {
            _mensaje.value = "El nombre no puede estar vacío"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Creamos el objeto Tablero.
                // IMPORTANTE: Asignamos el ID del usuario como 'owner'
                val nuevoTableroRequest = TableroRequest(
                    nombre_tablero = nombre,
                    owner = currentUser._id
                )
                val response = RetrofitInstance.api.crearTablero(nuevoTableroRequest)

                if (response.isSuccessful) {
                    _mensaje.value = "Tablero creado exitosamente"
                    cargarTableros() // Recargamos la lista para que aparezca el nuevo
                } else {
                    _mensaje.value = "Error al crear: ${response.code()}"
                }

            } catch (e: Exception) {
                _mensaje.value = "Error de conexión: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun limpiarMensaje() {
        _mensaje.value = null
    }
}