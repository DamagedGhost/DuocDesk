package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.viewmodel.GitHubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitHubRepoScreen(
    vm: GitHubViewModel = viewModel(),
    onBackClick: () -> Unit = {} // Agregamos opción para volver si quieres usarla en la TopBar
) {
    // 1. Estado local para guardar lo que escribe/pega el usuario
    var tokenInput by remember { mutableStateOf("") }

    // Obtenemos los estados del ViewModel
    val repos by vm.repos.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val error by vm.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repositorios GitHub") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------------------------------------------
            // SECCIÓN DE INPUT
            // ---------------------------------------------
            Text(
                "Ingresa tu Personal Access Token para ver tus repositorios:",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = tokenInput,
                onValueChange = { tokenInput = it },
                label = { Text("Pegar Token Aquí") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { vm.loadRepos(tokenInput) }, // ¡Aquí ocurre la magia!
                modifier = Modifier.fillMaxWidth(),
                enabled = tokenInput.isNotBlank() && !isLoading
            ) {
                Text("Cargar Repositorios")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // ---------------------------------------------
            // SECCIÓN DE RESULTADOS
            // ---------------------------------------------
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    error != null -> {
                        Text(
                            text = "Error: $error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    repos.isEmpty() -> {
                        Text(
                            text = "La lista está vacía.",
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(repos) { repo ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                                    )
                                ) {
                                    Column(Modifier.padding(16.dp)) {
                                        Text(
                                            repo.name,
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        repo.description?.let {
                                            Text(
                                                it,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }

                                        Spacer(Modifier.height(8.dp))
                                        Divider()
                                        Spacer(Modifier.height(8.dp))

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Lenguaje: ${repo.language ?: "N/A"}", style = MaterialTheme.typography.bodySmall)
                                            Text("⭐ ${repo.stargazers_count}", style = MaterialTheme.typography.bodySmall)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GitHubRepoScreenPreview() {
    GitHubRepoScreen()
}


