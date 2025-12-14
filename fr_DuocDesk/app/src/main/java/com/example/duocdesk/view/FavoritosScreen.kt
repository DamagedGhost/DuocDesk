package com.example.duocdesk.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Importante
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.duocdesk.viewmodel.PerfilViewModel
import com.example.duocdesk.viewmodel.TableroViewModel // Importante

@Composable
fun FavoritosScreen(
    navController: NavController,
    viewModel: TableroViewModel = viewModel(), // Recibimos el VM compartido
    onPerfilClick: () -> Unit = {},
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val photoUri by perfilViewModel.photoUri.collectAsState()

    // 1. OBTENEMOS LA LISTA COMPLETA
    val todosLosTableros by viewModel.tableros.collectAsState()

    // 2. FILTRAMOS SOLO LOS FAVORITOS
    val tablerosFavoritos = remember(todosLosTableros) {
        todosLosTableros.filter { it.esFavorito }
    }

    LaunchedEffect(Unit) {
        perfilViewModel.loadSavedPhoto(context)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                AnimatedIconButton(onClick = { navController.navigate("buscar") {launchSingleTop = true} }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                }
                AnimatedIconButton(onClick = {navController.navigate("tablero") {launchSingleTop = true}}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Tableros")
                }
                // En pantalla favoritos, el icono puede tener un color distinto o estar desactivado si quieres
                AnimatedIconButton(onClick = { /* Ya estamos aquí */ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos", tint = MaterialTheme.colorScheme.primary)
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
            // --- TOP BAR ---
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
                                model = photoUri, contentDescription = "Perfil",
                                modifier = Modifier.size(36.dp).clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                    Text("Favoritos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- CONTADOR ---
            Text(
                text = "Tienes ${tablerosFavoritos.size} tableros marcados",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- LISTA DE FAVORITOS ---
            if (tablerosFavoritos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes favoritos.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(tablerosFavoritos) { tablero ->
                        // 3. REUTILIZAMOS EL COMPONENTE TableroItem
                        TableroItem(
                            tablero = tablero,
                            onClick = {
                                // Navegamos al detalle igual que en la pantalla principal
                                navController.currentBackStackEntry?.savedStateHandle?.set("tablero", tablero)
                                navController.navigate("detail")
                            },
                            onFavClick = {
                                // Al quitar el favorito aquí, desaparecerá de la lista automáticamente
                                tablero._id?.let { id -> viewModel.toggleFavorito(id) }
                            }
                        )
                    }
                }
            }
        }
    }
}