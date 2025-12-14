package com.example.duocdesk.network.internal

import com.example.duocdesk.model.Notificacion
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.model.Usuario
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.example.duocdesk.model.TableroRequest

interface ApiService {

    // =========== USUARIOS ===========
    @GET("api/usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @POST("api/usuarios")
    suspend fun registrar(@Body usuario: Usuario): Response<Usuario>

    @PUT("api/usuarios/{id}")
    suspend fun actualizarPerfil(
        @Path("id") id: String,
        @Body usuario: Usuario
    ): Response<Map<String, String>>   // 🔥 EL BACKEND DEVUELVE MAP

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(
        @Path("id") id: String
    ): Response<Map<String, String>>
    // ===============================

    // =========== PERFIL USUARIO ===========
    @Multipart
    @POST("api/usuarios/{id}/foto")
    suspend fun subirFoto(
        @Path("id") id: String,
        @Part imagen: MultipartBody.Part
    ): Response<Map<String, String>>

    @GET("api/usuarios/{id}")
    suspend fun obtenerPerfil(
        @Path("id") id: String
    ): Response<Usuario>
    // =====================================

    // =========== LOGIN ===========
    @POST("api/usuarios/login")
    suspend fun login(@Body body: Map<String, String>): Response<Usuario>
    // ===========================

    // TABLEROS, LISTAS Y TARJETAS
    @GET("api/tableros")
    suspend fun getTableros(@Query("userId") userId: String? = null): Response<List<Tablero>>

    @POST("api/tableros")
    suspend fun crearTablero(@Body tablero: TableroRequest): Response<okhttp3.ResponseBody>

    // Obtener un solo tablero por ID (para ver el detalle fresco)
    @GET("api/tableros/{id}")
    suspend fun getTableroById(@Path("id") id: String): Response<Tablero>

    // Eliminar tablero
    @DELETE("api/tableros/{id}")
    suspend fun eliminarTablero(@Path("id") id: String): Response<Map<String, String>>

    // Invitar miembro (enviamos un Map con el email)
    @PUT("api/tableros/{id}/miembros")
    suspend fun invitarMiembro(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): Response<Tablero>

    // ===========================

    @GET("api/notificaciones")
    suspend fun getNotificaciones(@Query("userId") userId: String): Response<List<Notificacion>>

    // --- AGREGAR ESTO ---
    @PUT("api/notificaciones/{id}/leer")
    suspend fun marcarNotificacionLeida(@Path("id") id: String): Response<Map<String, String>>


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
