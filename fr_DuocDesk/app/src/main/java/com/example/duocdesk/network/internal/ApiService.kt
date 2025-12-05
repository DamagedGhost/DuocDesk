package com.example.duocdesk.network.internal

import com.example.duocdesk.model.Usuario
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    // -----------------------------
    // ENDPOINTS
    // -----------------------------

    @GET("api/usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @POST("api/usuarios")
    suspend fun registrar(@Body usuario: Usuario): Response<Usuario>

    @POST("api/usuarios/login")
    suspend fun login(@Body body: Map<String, String>): Response<Usuario>

    // --- SUBIR FOTO ---
    @Multipart
    @POST("api/usuarios/{id}/foto")
    suspend fun subirFoto(
        @Path("id") userId: String,
        @Part imagen: MultipartBody.Part
    ): Response<Map<String, String>>

    // --- PERFIL: GET + UPDATE ---
    @PUT("api/usuarios/{id}")
    suspend fun actualizarPerfil(
        @Path("id") id: String,
        @Body usuario: Usuario
    ): Response<Usuario>

    @GET("api/usuarios/{id}")
    suspend fun obtenerPerfil(
        @Path("id") id: String
    ): Response<Usuario>

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(
        @Path("id") id: String
    ): Response<Map<String, String>>



    // -----------------------------
    // CONSTRUCTOR RETROFIT AQUÍ MISMO
    // -----------------------------
    companion object {
        private const val BASE_URL = "http://98.91.150.2:4000/"

        fun crear(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
