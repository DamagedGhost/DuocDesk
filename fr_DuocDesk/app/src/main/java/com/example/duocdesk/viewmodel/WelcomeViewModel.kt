package com.example.duocdesk.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.duocdesk.model.WelcomeMessage

class WelcomeViewModel : ViewModel() {

    // Estado que contiene el mensaje de bienvenida, inicializado con un valor por defecto
    val welcomeMessage: MutableState<WelcomeMessage> =
        mutableStateOf(WelcomeMessage("¡Bienvenido a la aplicación!"))

    // Función para actualizar el mensaje, que puede ser llamada desde la UI
    fun updateMessage(newMessage: String) {
        welcomeMessage.value = WelcomeMessage(newMessage)
    }
}