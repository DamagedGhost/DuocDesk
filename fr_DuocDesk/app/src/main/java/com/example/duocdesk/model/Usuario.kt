package com.example.duocdesk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable

fun Usuario.getFotoUrl(): String? {
    if (this._id == null || this.fotoPerfilId == null) return null
    return "http://98.91.150.2:4000/api/usuarios/${this._id}/foto?id=${this.fotoPerfilId}"
}
