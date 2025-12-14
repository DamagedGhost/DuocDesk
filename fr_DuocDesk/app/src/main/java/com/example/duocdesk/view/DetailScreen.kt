package com.example.duocdesk.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.model.ListaTarea
import com.example.duocdesk.viewmodel.TableroDetailViewModel
import com.example.duocdesk.model.UserSession


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
    val currentUser = UserSession.currentUser
    val isOwner = tablero?.owner?._id == currentUser?._id

    var emailInvitado by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddListDialog by remember { mutableStateOf(false) }
    var newListName by remember { mutableStateOf("") }

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tablero?.nombre_tablero ?: "Cargando...") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Filled.ArrowBack, "Volver") }
                },
                actions = {
                    if (isOwner) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 100.dp) // Espacio al final
        ) {

            // ----------------------------------------------------
            // 1. HEADER: INFORMACIÓN DEL TABLERO
            // ----------------------------------------------------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Dueño: ${tablero?.owner?.nombre ?: "..."}", fontWeight = FontWeight.Bold)
                        Text("Correo: ${tablero?.owner?.email ?: "..."}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // ----------------------------------------------------
            // 2. SECCIÓN DE LISTAS (ESTILO TRELLO) 📋
            // ----------------------------------------------------
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Listas de Tareas", style = MaterialTheme.typography.titleMedium)

                    // Botón pequeño para agregar lista
                    TextButton(onClick = { showAddListDialog = true }) {
                        Icon(Icons.Filled.Add, null)
                        Text("Nueva Lista")
                    }
                }
            }

            item {
                // Scroll Horizontal para las listas
                LazyRow(
                    modifier = Modifier.fillMaxWidth().height(300.dp), // Altura fija para el carrusel
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Renderizamos las listas que vienen del backend
                    items(tablero?.listas ?: emptyList()) { lista ->
                        ListaItem(
                            lista = lista,
                            onDeleteClick = if (isOwner) {
                                { lista._id?.let { id -> viewModel.eliminarLista(id) } }
                            } else null,
                            onCreateCard = { titulo ->
                                lista._id?.let { idLista -> viewModel.crearTarjeta(idLista, titulo) }
                            },
                            onDeleteCard = { idTarjeta ->
                                lista._id?.let { idLista -> viewModel.eliminarTarjeta(idLista, idTarjeta) }
                            }
                            )
                    }

                    // Tarjeta final para agregar otra lista rápidamente
                    item {
                        Card(
                            modifier = Modifier
                                .width(250.dp)
                                .fillMaxHeight(),
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f)),
                            onClick = { showAddListDialog = true }
                        ) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("+ Agregar otra lista", color = Color.Gray)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // ----------------------------------------------------
            // 3. SECCIÓN INVITAR
            // ----------------------------------------------------
            item {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Divider()
                    Spacer(Modifier.height(16.dp))
                    Text("Invitar Miembros", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = emailInvitado,
                            onValueChange = { emailInvitado = it },
                            label = { Text("Correo del usuario") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.invitarMiembro(emailInvitado); emailInvitado = "" },
                            enabled = !isLoading
                        ) {
                            Icon(Icons.Filled.PersonAdd, null)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // ----------------------------------------------------
            // 4. LISTA DE MIEMBROS (ABAJO DEL TODO)
            // ----------------------------------------------------
            item {
                Text(
                    "Miembros (${tablero?.members?.size ?: 0})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(tablero?.members?.filterNotNull() ?: emptyList()) { miembro ->
                ListItem(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    headlineContent = { Text(miembro.nombre) },
                    supportingContent = { Text(miembro.email) },
                    leadingContent = { Icon(Icons.Filled.Person, null) },
                    trailingContent = {
                        if (isOwner && miembro._id != currentUser?._id) {
                            IconButton(onClick = { miembro._id?.let { viewModel.eliminarMiembro(it) } }) {
                                Icon(Icons.Filled.Close, null, tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                )
                Divider(Modifier.padding(horizontal = 16.dp))
            }
        }

        // --- DIALOGO CREAR LISTA ---
        if (showAddListDialog) {
            AlertDialog(
                onDismissRequest = { showAddListDialog = false },
                title = { Text("Nueva Lista") },
                text = {
                    OutlinedTextField(
                        value = newListName,
                        onValueChange = { newListName = it },
                        label = { Text("Título (ej: Por hacer)") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.crearLista(newListName)
                        newListName = ""
                        showAddListDialog = false
                    }) { Text("Crear") }
                },
                dismissButton = {
                    TextButton(onClick = { showAddListDialog = false }) { Text("Cancelar") }
                }
            )
        }

        // --- DIALOGO ELIMINAR TABLERO ---
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("¿Eliminar Tablero?") },
                text = { Text("Se borrará todo permanentemente.") },
                confirmButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false; viewModel.eliminarTablero(onBackClick) },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) { Text("Eliminar") }
                },
                dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") } }
            )
        }
    }
}

// 🎨 COMPONENTE PARA DIBUJAR UNA LISTA (COLUMNA)
@Composable
fun ListaItem(
    lista: ListaTarea,
    onDeleteClick: (() -> Unit)? = null,
    onCreateCard: (String) -> Unit = {},
    onDeleteCard: (String) -> Unit = {}
) {
    var showAddCardDialog by remember { mutableStateOf(false) }
    var newCardTitle by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // --- HEADER LISTA ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lista.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (onDeleteClick != null) {
                    IconButton(onClick = onDeleteClick, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Filled.Close, "Borrar lista", tint = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- LISTA DE TARJETAS (Scroll vertical interno) ---
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f).fillMaxWidth() // Ocupa espacio disponible
            ) {
                if (lista.tarjetas.isEmpty()) {
                    Text("Sin tareas", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(4.dp))
                }

                // Renderizamos cada tarjeta
                lista.tarjetas.forEach { tarjeta ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tarjeta.titulo,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            // Botón borrar tarjeta
                            IconButton(
                                onClick = { tarjeta._id?.let { onDeleteCard(it) } },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(Icons.Filled.Close, "Borrar", tint = Color.LightGray)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- BOTÓN AÑADIR TARJETA ---
            Button(
                onClick = { showAddCardDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.1f), contentColor = Color.Black)
            ) {
                Text("+ Añadir tarjeta")
            }
        }
    }

    // --- DIALOGO AÑADIR TARJETA ---
    if (showAddCardDialog) {
        AlertDialog(
            onDismissRequest = { showAddCardDialog = false },
            title = { Text("Nueva Tarea") },
            text = {
                OutlinedTextField(
                    value = newCardTitle,
                    onValueChange = { newCardTitle = it },
                    label = { Text("Descripción") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (newCardTitle.isNotBlank()) {
                        onCreateCard(newCardTitle)
                        newCardTitle = ""
                        showAddCardDialog = false
                    }
                }) { Text("Añadir") }
            },
            dismissButton = {
                TextButton(onClick = { showAddCardDialog = false }) { Text("Cancelar") }
            }
        )
    }
}
