package com.example.duocdesk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.RetrofitInstance
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    // LiveData privado para modificar internamente
    private val _usuarios = MutableLiveData<List<Usuario>>()
    // LiveData público e inmutable para observar desde la UI
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    // LiveData para manejar errores (puedes hacerlo más sofisticado)
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String> get() = _error as LiveData<String>

    // LiveData para el estado de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    init {
        // Llama a la API cuando el ViewModel se crea
        fetchUsuarios()
    }

    fun fetchUsuarios() {
        // Inicia el indicador de carga
        _isLoading.value = true
        // Usamos viewModelScope para lanzar una coroutine ligada al ciclo de vida del ViewModel
        viewModelScope.launch {
            try {
                // Llama al metodo suspend de la interfaz ApiService
                val response = RetrofitInstance.api.getAllUsuarios()

                if (response.isSuccessful) {
                    // Si la respuesta es exitosa, actualiza el LiveData _usuarios
                    _usuarios.postValue(response.body()) // postValue porque estamos en coroutine
                    _error.postValue(null) // Limpia errores previos
                } else {
                    // Si hay un error HTTP (ej: 404, 500)
                    _error.postValue("Error ${response.code()}: ${response.message()}")
                    _usuarios.postValue(emptyList()) // Lista vacía en caso de error
                }
            } catch (e: Exception) {
                // Si hay un error de red o de parseo JSON
                _error.postValue("Error de conexión: ${e.message}")
                _usuarios.postValue(emptyList())
            } finally {
                // Detiene el indicador de carga, tanto si fue exitoso como si falló
                _isLoading.postValue(false)
            }
        }
    }
}