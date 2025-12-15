package com.example.duocdesk.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.compose.AsyncImage
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.viewmodel.PerfilViewModel
import com.example.duocdesk.viewmodel.TableroViewModel
import com.example.duocdesk.model.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableroScreen(
    onPerfilClick: () -> Unit = {},
    onBuscarClick: () -> Unit = {},
    onFavoritosClick: () -> Unit = {},
    onFiltrarClick: () -> Unit = {},
    onGitHubClick: () -> Unit = {},
    onTableroClick: (Tablero) -> Unit = {},
    // ViewModels inyectados
    tableroViewModel: TableroViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val currentUser = UserSession.currentUser
    val isAdmin = currentUser?.rolGlobal == "ADMIN"
    val topBarColor = if (isAdmin) Color(0xFFFFD700) else MaterialTheme.colorScheme.surface // Dorado si es Admin
    val titleText = if (isAdmin) "ADMIN" else "Tablero"

    val context = LocalContext.current
    val photoUri by perfilViewModel.photoUri.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var newTableroName by remember { mutableStateOf("") }

    val mensaje by tableroViewModel.mensaje.collectAsState()
    val tableros by tableroViewModel.tableros.collectAsState()
    val isLoading by tableroViewModel.isLoading.collectAsState()
    val notificacion by tableroViewModel.ultimaNotificacion.collectAsState()

    // Estado para el Pull to Refresh
    val isRefreshing by tableroViewModel.isRefreshing.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState() // Mantenemos el estado por si quieres personalizarlo

    // --- FUNCIÓN DE NOTIFICACIONES ---
    fun mostrarNotificacionSistema(context: android.content.Context, titulo: String, mensaje: String) {
        val channelId = "invitaciones_channel"
        val notificationId = System.currentTimeMillis().toInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Invitaciones"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = "Notificaciones de nuevos tableros"
            }
            val notificationManager = context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(notificationId, builder.build())
        }
    }

    // Efectos
    LaunchedEffect(notificacion) {
        notificacion?.let {
            mostrarNotificacionSistema(context, "DuocDesk", it)
            Toast.makeText(context, "🔔 $it", Toast.LENGTH_LONG).show()
            tableroViewModel.limpiarNotificacion()
        }
    }

    LaunchedEffect(mensaje) {
        mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            tableroViewModel.limpiarMensaje()
        }
    }

    LaunchedEffect(Unit) {
        perfilViewModel.loadSavedPhoto(context)
        // Ya no cargamos forzosamente aquí si tienes persistencia,
        // pero está bien para asegurar datos frescos al entrar
        tableroViewModel.cargarTableros()
    }

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { showCreateDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.Add, "Crear Tablero")
                }
                ExtendedFloatingActionButton(
                    onClick = { tableroViewModel.abrirReservaSala(context) },
                    icon = { Icon(Icons.Filled.DateRange, "Reservar") },
                    text = { Text("Reservar Sala") },
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                AnimatedIconButton(onClick = onBuscarClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                }
                AnimatedIconButton(onClick = onFiltrarClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Tableros", tint = MaterialTheme.colorScheme.primary)
                }
                AnimatedIconButton(onClick = onFavoritosClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {

            // ---------------------------------------------------------
            // 1. ZONA FIJA (Header, Botón Github, Título)
            //    Esta parte NO se mueve ni se refresca al deslizar
            // ---------------------------------------------------------

            // Top Bar Personalizada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topBarColor) // <--- Color dinámico
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedIconButton(onClick = onPerfilClick) {
                        if (photoUri != null) {
                            AsyncImage(
                                model = photoUri,
                                contentDescription = "Perfil",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // TÍTULO DINÁMICO
                    Text(
                        text = titleText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if(isAdmin) Color.Black else MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGitHubClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Ver mis repositorios de GitHub")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Mis Tableros",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ---------------------------------------------------------
            // 2. ZONA DESLIZABLE (Lista de Tableros)
            //    Usa 'weight(1f)' para ocupar todo el espacio restante.
            //    Aquí aplicamos el PullToRefreshBox
            // ---------------------------------------------------------
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { tableroViewModel.refresh() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // <--- CLAVE: Ocupa todo el espacio sobrante
            ) {
                if (isLoading && !isRefreshing) {
                    // Carga inicial (spinner al centro)
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (tableros.isEmpty()) {
                    // Lista vacía
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay tableros. ¡Desliza para actualizar!", color = Color.Gray)
                    }
                } else {
                    // Lista con datos
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize(),
                        // Padding extra abajo para que el FAB no tape el último item
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 100.dp
                        )
                    ) {
                        items(tableros) { tablero ->
                            TableroItem(
                                tablero = tablero,
                                onClick = { onTableroClick(tablero) },
                                onFavClick = {
                                    tablero._id?.let { id -> tableroViewModel.toggleFavorito(id) }
                                }
                            )
                        }
                    }
                }
            }
        }

        // DIALOGO DE CREACIÓN
        if (showCreateDialog) {
            AlertDialog(
                onDismissRequest = { showCreateDialog = false },
                title = { Text("Nuevo Tablero") },
                text = {
                    Column {
                        Text("Ponle un nombre a tu espacio de trabajo:")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newTableroName,
                            onValueChange = { newTableroName = it },
                            label = { Text("Nombre del tablero") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newTableroName.isNotBlank()) {
                                tableroViewModel.crearTablero(newTableroName)
                                newTableroName = ""
                                showCreateDialog = false
                            }
                        }
                    ) { Text("Crear") }
                },
                dismissButton = {
                    TextButton(onClick = { showCreateDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Composable
fun TableroItem(
    tablero: Tablero,
    onClick: () -> Unit,
    onFavClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Hacemos clickeable toda la tarjeta
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tablero.nombre_tablero,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${tablero.listas.size} listas activas",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedIconButton(onClick = onFavClick) {
                Icon(
                    imageVector = if (tablero.esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (tablero.esFavorito) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun TableroScreen() {
    TableroScreen(
    )
}