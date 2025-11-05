package com.example.duocdesk.network

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.duocdesk.R // Asegúrate que R se importe correctamente
import com.example.duocdesk.model.Usuario

class UsuarioAdapter (private var usuarios: List<Usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

        // Clase interna ViewHolder que contiene las vistas de cada item
        class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombre)
            val correoTextView: TextView = itemView.findViewById(R.id.textViewCorreo)
        }

        // Crea nuevas vistas (invocado por el layout manager)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_usuario, parent, false)
            return UsuarioViewHolder(view)
        }

        // Reemplaza el contenido de una vista (invocado por el layout manager)
        override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
            val usuario = usuarios[position]
            holder.nombreTextView.text = "${usuario.nombre ?: ""} ${usuario.apellido ?: ""}" // Maneja nulos
            holder.correoTextView.text = usuario.correo ?: "Correo no disponible"
        }

        // Devuelve el tamaño de tu dataset (invocado por el layout manager)
        override fun getItemCount() = usuarios.size

        // Función para actualizar la lista de usuarios en el adaptador
        fun updateUsuarios(newUsuarios: List<Usuario>) {
            usuarios = newUsuarios
            notifyDataSetChanged() // Notifica al RecyclerView que los datos cambiaron
        }
}