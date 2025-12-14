package com.example.duocdesk.util

import android.content.Context
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.model.UserSession
import com.google.gson.Gson
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(usuario: Usuario) {
        val json = gson.toJson(usuario)
        prefs.edit { putString("current_user", json) }
        UserSession.currentUser = usuario // Actualizamos la sesión en memoria RAM
    }

    fun getUser(): Usuario? {
        val json = prefs.getString("current_user", null)
        return if (json != null) {
            val usuario = gson.fromJson(json, Usuario::class.java)
            UserSession.currentUser = usuario // Restauramos sesión RAM
            usuario
        } else {
            null
        }
    }

    fun logout() {
        prefs.edit { clear() }
        UserSession.currentUser = null
    }
}