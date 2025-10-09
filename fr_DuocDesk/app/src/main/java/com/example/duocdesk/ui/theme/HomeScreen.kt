package com.example.duocdesk.ui.theme

// Importaciones necesarias
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.duocdesk.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val isClicked = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
    Text(text = "Bienvenido a DuocDesk")
    Button(
        onClick = { isClicked.value = !isClicked.value },
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (isClicked.value) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = "Iniciar Sesión")
    }
    // Estructura básica de la pantalla con Scaffold: AppBar, contenido principal, etc.
Scaffold(
    // Barra superior
    topBar = {
        // parametros de la barra superior
        TopAppBar(
            // Título de la barra
            title = { Text("DuocDesk") },
            // Colores de la barra
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Bienvenido a DuocDesk",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)

            )
            Image(
                painter = painterResource(id = R.drawable.duoc_desk), // cambiar por el logo real
                contentDescription = "Logo de la app DuocDesk",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
            Button(onClick = { isClicked }) {
                Text(text = "Iniciar Sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

