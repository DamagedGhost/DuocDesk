package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // <-- Importación clave para el error 3
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState // <-- Importación del error 1 (requiere dependencia)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.viewmodel.UsuarioViewModel

@Composable
fun UserListScreen(viewModel: UsuarioViewModel = viewModel()) {

    // Corregido (Error 2): Especificar el tipo <Usuario>
    val usuarios by viewModel.usuarios.observeAsState(emptyList<Usuario>())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null) // Esto funciona bien con el VM corregido

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else if (usuarios.isEmpty()) {
            Text(text = "No hay usuarios para mostrar.", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Esto ahora funciona gracias a la importación (Error 3)
                items(usuarios) { usuario ->
                    UsuarioItem(usuario = usuario)
                }
            }
        }
    }
}

@Composable
fun UsuarioItem(usuario: Usuario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${usuario.nombre ?: "Sin"} ${usuario.apellido ?: "Nombre"}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = usuario.correo ?: "Correo no disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UsuarioItemPreview() {
    val usuario = Usuario(1, "test@duoc.cl", "Nombre", "Apellido", "Ingeniería")
    UsuarioItem(usuario = usuario)
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    UserListScreen()
}