package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.viewmodel.GitHubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitHubRepoScreen(
    token: String,
    vm: GitHubViewModel = viewModel()
) {
    // Dispara la carga SOLO una vez
    LaunchedEffect(Unit) {
        vm.loadRepos(token)
    }

    val repos by vm.repos.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val error by vm.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis repositorios de GitHub") }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                error != null -> {
                    Text(
                        text = "No se pudieron cargar los repos:\n$error",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                repos.isEmpty() -> {
                    Text(
                        text = "No hay repositorios para mostrar.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(repos) { repo ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(4.dp)
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

                                    Text("Lenguaje: ${repo.language ?: "Desconocido"}")
                                    Text(" ${repo.stargazers_count}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
