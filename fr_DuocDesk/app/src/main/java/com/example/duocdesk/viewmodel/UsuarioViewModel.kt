package com.example.duocdesk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.network.internal.RetrofitInstance   // ←🔥 IMPORT QUE FALTABA
import kotlinx.coroutines.launch


class UsuarioViewModel : ViewModel() {

    private val _usuarios = MutableLiveData<List<Usuario>>()  // 👈 Ahora Usuario SÍ existe
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    init {
        fetchUsuarios()
    }

    fun fetchUsuarios() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUsuarios()


                if (response.isSuccessful) {
                    _usuarios.postValue(response.body())
                    _error.postValue(null)
                } else {
                    _error.postValue("Error ${response.code()}: ${response.message()}")
                    _usuarios.postValue(emptyList())
                }

            } catch (e: Exception) {
                _error.postValue("Error de conexión: ${e.message}")
                _usuarios.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
