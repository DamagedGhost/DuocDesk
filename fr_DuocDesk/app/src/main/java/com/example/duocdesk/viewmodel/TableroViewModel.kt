package com.example.duocdesk.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.network.internal.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.duocdesk.model.TableroRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class TableroViewModel : ViewModel() {

    private val _tableros = MutableStateFlow<List<Tablero>>(emptyList())
    val tableros = _tableros.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Para la búsqueda
    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda = _textoBusqueda.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    // estado para mostrar notificaciones en UI
    private val _ultimaNotificacion = MutableStateFlow<String?>(null)
    val ultimaNotificacion = _ultimaNotificacion.asStateFlow()

    // NUEVO: Estado específico para el Pull-to-Refresh
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        cargarTableros()
        iniciarPolling()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            cargarTableros() // Reutilizamos tu función de carga
            _isRefreshing.value = false
        }
    }

    private fun iniciarPolling() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    val userId = UserSession.currentUser?._id
                    if (userId != null) {
                        val response = RetrofitInstance.api.getNotificaciones(userId)
                        if (response.isSuccessful && response.body() != null) {
                            val notificaciones = response.body()!!

                            // Si hay notificaciones pendientes (el backend solo devuelve las NO leídas)
                            if (notificaciones.isNotEmpty()) {
                                val noti = notificaciones[0]

                                // 1. Mostrar mensaje en UI
                                _ultimaNotificacion.value = noti.mensaje

                                // 2. IMPORTANTE: Marcar como leída en el servidor para que no vuelva a salir
                                marcarComoLeida(noti._id)

                                // 3. ELIMINADO: cargarTableros()
                                // Ya no recargamos la lista automáticamente para evitar el bucle visual.
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(10000) // Esperar 10 segundos
            }
        }
    }

    // Función auxiliar para matar la notificación en el server
    private fun marcarComoLeida(notiId: String) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.marcarNotificacionLeida(notiId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun limpiarNotificacion() {
        _ultimaNotificacion.value = null
    }

    fun cargarTableros() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("TableroViewModel", "Iniciando petición a la API...") // Log de depuración

                val currentUser = UserSession.currentUser
                val response = RetrofitInstance.api.getTableros(currentUser?._id)

                Log.d("TableroViewModel", "Código de respuesta: ${response.code()}") // Ver código HTTP

                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    Log.d("TableroViewModel", "Tableros recibidos: ${lista.size}")
                    _tableros.value = lista
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("TableroViewModel", "Error del servidor: $errorBody") // Imprimir error del backend
                    _mensaje.value = "Error servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                // 🔥 ESTA ES LA PARTE IMPORTANTE 🔥
                Log.e("TableroViewModel", "EXCEPCIÓN FATAL", e) // Imprime todo el error rojo en Logcat

                // Mostrar un resumen en el mensaje
                _mensaje.value = "Error: ${e.javaClass.simpleName} - ${e.message}"
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