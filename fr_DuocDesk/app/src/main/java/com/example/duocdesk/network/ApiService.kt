package com.example.duocdesk.network

import com.example.duocdesk.model.Usuario
import retrofit2.Response // Importante usar Response para manejo de errores
import retrofit2.http.GET

// Interfaz para definir las llamadas a la API usando Retrofit
interface ApiService {

    // Define la petición GET al endpoint específico
    @GET("api/usuarios/listar")
    suspend fun getAllUsuarios(): Response<List<Usuario>> // suspend para Coroutines
}