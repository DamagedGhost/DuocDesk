package com.example.duocdesk.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tablero(
    val _id: String? = null,
    val nombre_tablero: String,
    val owner: Usuario? = null,
    val members: List<Usuario> = emptyList(),
    val listas: List<ListaTarea> = emptyList(),
    val fechaCreacion: String? = null,
    var esFavorito: Boolean = false // Campo local para la UI de favoritos
): Parcelable

@Parcelize
data class ListaTarea(
    val titulo: String,
    val tarjetas: List<Tarjeta> = emptyList()
): Parcelable

@Parcelize
data class Tarjeta(
    val titulo: String,
    val descripcion: String,
    val prioridad: String = "Media"
): Parcelable