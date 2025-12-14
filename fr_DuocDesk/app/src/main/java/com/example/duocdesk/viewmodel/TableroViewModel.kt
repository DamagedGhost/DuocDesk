package com.example.duocdesk.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

class TableroViewModel(application: Application) : AndroidViewModel(application) {

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

    // Memoria para guardar los IDs favoritos
    private val idsFavoritos = mutableSetOf<String>()

    init {
        cargarTableros()
        iniciarPolling()
    }

    private fun getPrefs() = getApplication<Application>().getSharedPreferences("duocdesk_favs", Context.MODE_PRIVATE)

    private fun cargarFavoritosLocales() {
        val userId = UserSession.currentUser?._id ?: return
        // Buscamos los favoritos guardados bajo la llave "favs_ID_USUARIO"
        val guardados = getPrefs().getStringSet("favs_$userId", emptySet())

        idsFavoritos.clear() // Limpiamos la memoria anterior (por si cambió de usuario)
        if (guardados != null) {
            idsFavoritos.addAll(guardados)
        }
    }

    private fun guardarFavoritosLocales() {
        val userId = UserSession.currentUser?._id ?: return
        // Guardamos el set actual asociado al ID del usuario
        getPrefs().edit().putStringSet("favs_$userId", idsFavoritos.toSet()).apply()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            cargarTableros() // función de carga
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
                cargarFavoritosLocales()
                Log.d("TableroViewModel", "Iniciando petición a la API...") // Log de depuración

                val currentUser = UserSession.currentUser
                val response = RetrofitInstance.api.getTableros(currentUser?._id)

                Log.d("TableroViewModel", "Código de respuesta: ${response.code()}") // Ver código HTTP

                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    val listaFusionada = lista.map { tablero ->
                        if (idsFavoritos.contains(tablero._id)) {
                            tablero.copy(esFavorito = true)
                        } else {
                            tablero
                        }
                    }
                    _tableros.value = listaFusionada
                    Log.d("TableroViewModel", "Tableros cargados y fusionados: ${listaFusionada.size}")

                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("TableroViewModel", "Error del servidor: $errorBody") // Imprimir error del backend
                    _mensaje.value = "Error servidor: ${response.code()}"
                }
            } catch (e: Exception) {
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
        // ACTUALIZAR MEMORIA Y LISTA VISUAL

        // Actualizamos el Set de IDs (Nuestra "Base de datos" en memoria)
        if (idsFavoritos.contains(tableroId)) {
            idsFavoritos.remove(tableroId)
        } else {
            idsFavoritos.add(tableroId)
        }
        //Actualizar almacenamiento persistente
        guardarFavoritosLocales()
        // Actualizamos la lista observable (La UI)
        val listaActualizada = _tableros.value.map {
            if (it._id == tableroId) {
                // Invertimos el valor actual
                it.copy(esFavorito = !it.esFavorito)
            } else {
                it
            }
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