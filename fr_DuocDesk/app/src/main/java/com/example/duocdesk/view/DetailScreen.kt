package com.example.duocdesk.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.viewmodel.TableroDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    tableroParam: Tablero?, // Recibimos el objeto tablero
    onBackClick: () -> Unit = {},
    viewModel: TableroDetailViewModel = viewModel()
) {
    val context = LocalContext.current
    val tablero by viewModel.tablero.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Inicializamos el ViewModel con el tablero que recibimos
    LaunchedEffect(tableroParam) {
        if (tableroParam != null) {
            viewModel.setTableroInicial(tableroParam)
        }
    }

    // Feedback visual (Toasts)
    LaunchedEffect(mensaje) {
        mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMensaje()
        }
    }

    // Variable para el input de invitación
    var emailInvitado by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tablero?.nombre_tablero ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Volver")
                    }
                },
                actions = {
                    // Botón Eliminar
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Filled.Delete, "Eliminar Tablero", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (tablero != null) {
                // 1. INFO DEL OWNER
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Información del Tablero", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Text("Dueño: ${tablero?.owner?.nombre ?: "Desconocido"}")
                        Text("Correo: ${tablero?.owner?.email ?: "-"}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 2. SECCIÓN INVITAR
                Text("Invitar Miembros", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = emailInvitado,
                        onValueChange = { emailInvitado = it },
                        label = { Text("Correo del usuario") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            viewModel.invitarMiembro(emailInvitado)
                            emailInvitado = ""
                        },
                        enabled = !isLoading
                    ) {
                        Icon(Icons.Filled.PersonAdd, null)
                    }
                }

                Spacer(Modifier.height(24.dp))

                // 3. LISTA DE MIEMBROS
                Text("Miembros del Equipo (${tablero?.members?.size ?: 0})", style = MaterialTheme.typography.titleMedium)
                LazyColumn(
                    contentPadding = PaddingValues(top = 8.dp)
                ) {
                    items(tablero?.members ?: emptyList()) { miembro ->
                        ListItem(
                            headlineContent = { Text(miembro.nombre) },
                            supportingContent = { Text(miembro.email) },
                            leadingContent = { Icon(Icons.Filled.Person, null) }
                        )
                        Divider()
                    }
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        // DIALOGO DE CONFIRMACIÓN DE ELIMINACIÓN
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("¿Eliminar Tablero?") },
                text = { Text("Esta acción borrará el tablero y todas sus listas permanentemente.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            viewModel.eliminarTablero(onSuccess = onBackClick)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}
