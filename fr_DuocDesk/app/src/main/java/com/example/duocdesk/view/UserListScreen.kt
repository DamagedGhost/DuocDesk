package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState     // 👈 IMPORT CLAVE
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.viewmodel.UsuarioViewModel

@Composable
fun UserListScreen(viewModel: UsuarioViewModel = viewModel()) {

    val usuarios by viewModel.usuarios.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()

            error != null -> Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )

            usuarios.isEmpty() -> Text(
                text = "No hay usuarios registrados.",
                modifier = Modifier.padding(16.dp)
            )

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(usuarios) { usuario ->
                        UsuarioItem(usuario)
                    }
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
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = usuario.nombre,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = usuario.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rol: ${usuario.rolGlobal}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
