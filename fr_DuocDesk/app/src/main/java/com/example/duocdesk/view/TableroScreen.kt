package com.example.duocdesk.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import coil.compose.AsyncImage
import com.example.duocdesk.model.Tablero
import com.example.duocdesk.viewmodel.PerfilViewModel
import com.example.duocdesk.viewmodel.TableroViewModel

@Composable
fun TableroScreen(
    onPerfilClick: () -> Unit = {},
    onBuscarClick: () -> Unit = {},
    onFavoritosClick: () -> Unit = {},
    onFiltrarClick: () -> Unit = {},
    onGitHubClick: () -> Unit = {},
            // ViewModels inyectados
    tableroViewModel: TableroViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val photoUri by perfilViewModel.photoUri.collectAsState()

    // Obtenemos los tableros del backend
    val tableros by tableroViewModel.tableros.collectAsState()
    val isLoading by tableroViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        perfilViewModel.loadSavedPhoto(context)
        tableroViewModel.cargarTableros() // Recargar al entrar
    }

    Scaffold(
        // --- BOTÓN FLOTANTE PARA RESERVAR SALA ---
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { tableroViewModel.abrirReservaSala(context) },
                icon = { Icon(Icons.Filled.DateRange, "Reservar") },
                text = { Text("Reservar Sala") },
                containerColor = MaterialTheme.colorScheme.secondary
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                AnimatedIconButton(onClick = onBuscarClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                }
                AnimatedIconButton(onClick = onFavoritosClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                }
                AnimatedIconButton(onClick = onFiltrarClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
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

            // TOP BAR
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
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

                    Text("Tablero", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //BOTÓN GITHUB
            Button(
                onClick = onGitHubClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Ver mis repositorios de GitHub")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Mis Tableros", style = MaterialTheme.typography.titleLarge)

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (tableros.isEmpty()) {
                // Muestra esto si no hay datos
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes tableros aún. ¡Crea uno!", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(tableros) { tablero ->
                        TableroItem(
                            tablero = tablero,
                            onFavClick = {
                                // SUGERENCIA DE SEGURIDAD: Evita el !! si es posible
                                tablero._id?.let { id -> tableroViewModel.toggleFavorito(id) }
                            }
                        )
                    }
                    //TODO: Botón para crear tablero ficticio (para probar)
                    item {
                        OutlinedButton(
                            onClick = { /* Lógica crear tablero */ },
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 8.dp) // Un poco de estilo extra
                        ) {
                            Text("+ Nuevo Tablero")
                        } // <--- ¡ESTA LLAVE FALTABA!
                    }
                }
            }
        }
    }
}

@Composable
fun TableroItem(tablero: Tablero, onFavClick: () -> Unit) {
    Card(
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
            Column {
                Text(tablero.nombre_tablero, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("${tablero.listas.size} listas activas", fontSize = 14.sp)
            }
            IconButton(onClick = onFavClick) {
                Icon(
                    imageVector = if (tablero.esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (tablero.esFavorito) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TableroScreenPreview() {
    TableroScreen()
}
