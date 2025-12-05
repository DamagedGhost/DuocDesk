package com.example.duocdesk.model

import com.example.duocdesk.network.internal.RetrofitInstance

data class Usuario(
    val _id: String? = null,
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val carrera: String = "",
    val edad: Int = 0,
    val rolGlobal: String = "USER",
    val fotoPerfilId: String? = null
)

fun Usuario.getFotoUrl(): String? {
    if (this._id == null || this.fotoPerfilId == null) return null
    // Agregamos un timestamp "falso" basado en el ID de la foto para que
    // si cambia el ID, cambie la URL y Coil la descargue de nuevo.
    return "http://98.91.150.2:4000/api/usuarios/${this._id}/foto?id=${this.fotoPerfilId}"
}

