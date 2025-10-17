package com.example.duocdesk.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.duocdesk.model.WelcomeMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel que maneja la lógica de la pantalla de bienvenida:
// mantiene el estado del mensaje y simula una carga inicial

class WelcomeViewModel : ViewModel() {

    // Estado que contiene el mensaje de bienvenida, inicializado con un valor por defecto
    val welcomeMessage: MutableState<WelcomeMessage> =
        mutableStateOf(WelcomeMessage("¡Bienvenido a la aplicación!"))

    // Función para actualizar el mensaje, que puede ser llamada desde la UI
    fun updateMessage(newMessage: String) {
        welcomeMessage.value = WelcomeMessage(newMessage)
    }

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            // Simula alguna inicialización o carga de datos
            delay(2000) // Simula un retardo de 2 segundos
            _isReady.value = true // Indica que la aplicación está lista
        }
    }

}