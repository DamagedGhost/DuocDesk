package com.example.duocdesk.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    private val _photoUri = MutableStateFlow<String?>(null)
    val photoUri = _photoUri.asStateFlow()

    fun loadSavedPhoto(context: Context) {
        val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
        _photoUri.value = prefs.getString("photo_uri", null)
    }

    fun updatePhoto(context: Context, uri: String) {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences("perfil_prefs", Context.MODE_PRIVATE)
            prefs.edit().putString("photo_uri", uri).apply()
            _photoUri.value = uri
        }
    }
}
