package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfilScreen(onCloseClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onCloseClick) {
                    Icon(Icons.Filled.Close, contentDescription = "Cerrar")
                }
            }

            Icon(
                Icons.Filled.Person,
                contentDescription = "Perfil",
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("[Name]", fontSize = 20.sp)
            Text("Sede: San Bernardo", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Divider()

            PerfilItem("Perfil")
            PerfilItem("Notificaciones")
            PerfilItem("Foros")
            PerfilItem("Favoritos")
            PerfilItem("Configuración")
        }
    }
}

@Composable
fun PerfilItem(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(text, fontSize = 18.sp)
        Divider()
    }
}
