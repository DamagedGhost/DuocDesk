package com.example.duocdesk.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.duocdesk.model.getFotoUrl
import com.example.duocdesk.viewmodel.PerfilViewModel
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.runtime.DisposableEffect
// Agrega estos imports al inicio del archivo
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun TableroScreen(
    onPerfilClick: () -> Unit = {},
    onBuscarClick: () -> Unit = {},
    onFavoritosClick: () -> Unit = {},
    onFiltrarClick: () -> Unit = {},
    onGitHubClick: () -> Unit = {}
) {
    var currentUser by remember { mutableStateOf(com.example.duocdesk.model.UserSession.currentUser) }

    val context = LocalContext.current
    val viewModel: PerfilViewModel = viewModel()
    val photoUri by viewModel.photoUri.collectAsState()

    var tareas by remember {
        mutableStateOf(listOf("Tarea N°1", "Tarea N°2", "Tarea N°3", "Tarea N°4"))
    }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentUser = com.example.duocdesk.model.UserSession.currentUser
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        currentUser = com.example.duocdesk.model.UserSession.currentUser
    }

    LaunchedEffect(Unit) {
        viewModel.loadSavedPhoto(context)
    }

    Scaffold(
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
                        val fotoUrl = currentUser?.getFotoUrl()
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

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Planificación de sprint",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                tareas.forEach { tarea ->
                    OutlinedTextField(
                        value = tarea,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }

                OutlinedButton(
                    onClick = { tareas = tareas + "Nueva tarea" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("+ Agregar tarea")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TableroScreenPreview() {
    TableroScreen()
}
