package com.example.duocdesk.model

data class Tablero(
    val _id: String? = null,
    val nombre_tablero: String,
    val owner: String, // ID del usuario dueño
    val listas: List<ListaTarea> = emptyList(),
    val fechaCreacion: String? = null,
    var esFavorito: Boolean = false // Campo local para la UI de favoritos
)

data class ListaTarea(
    val titulo: String,
    val tarjetas: List<Tarjeta> = emptyList()
)

data class Tarjeta(
    val titulo: String,
    val descripcion: String,
    val prioridad: String = "Media"
)