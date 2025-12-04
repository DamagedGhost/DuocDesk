package com.example.duocdesk.network.internal

import retrofit2.Response
import retrofit2.http.*
import com.example.duocdesk.model.Usuario

interface ApiService {

    @GET("api/usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @POST("api/usuarios")
    suspend fun registrar(
        @Body usuario: Usuario
    ): Response<Usuario>

    @POST("api/usuarios/login")
    suspend fun login(
        @Body body: Map<String, String>
    ): Response<Usuario>
}

