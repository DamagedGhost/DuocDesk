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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.duocdesk.viewmodel.PerfilViewModel
import com.example.duocdesk.viewmodel.TableroViewModel

@Composable
fun SearchResultScreen(
    navController: NavController,
    viewModel: TableroViewModel,
    onPerfilClick: () -> Unit = {},
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val photoUri by perfilViewModel.photoUri.collectAsState()
    val resultados by viewModel.resultadosBusqueda.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    LaunchedEffect(Unit) {
        perfilViewModel.loadSavedPhoto(context)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                // ... (Iconos de navegación igual que antes) ...
                AnimatedIconButton(onClick = { navController.navigate("buscar") {launchSingleTop = true} }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar", tint = MaterialTheme.colorScheme.primary)
                }
                AnimatedIconButton(onClick = {navController.navigate("tablero") {launchSingleTop = true}}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Tableros")
                }
                AnimatedIconButton(onClick = { navController.navigate("favoritos") {launchSingleTop = true}}, modifier = Modifier.weight(1f)) {
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
            // Header
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
                    Text("Resultados", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { viewModel.onBusquedaChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text("Buscar tablero...") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Search, null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${resultados.size} Tableros encontrados",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // LISTA DE RESULTADOS
            if (resultados.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron coincidencias.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(resultados) { tablero ->
                        // Reutilizamos tu TableroItem
                        TableroItem(
                            tablero = tablero,
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set("tablero", tablero)
                                navController.navigate("detail")
                            },
                            onFavClick = {
                                tablero._id?.let { viewModel.toggleFavorito(it) }
                            }
                        )
                    }
                }
            }
        }
    }
}