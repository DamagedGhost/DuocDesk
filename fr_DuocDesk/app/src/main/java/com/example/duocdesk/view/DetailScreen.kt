package com.example.duocdesk.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duocdesk.R

@Composable
fun DetailScreen(onPerfilClick: () -> Unit = {}, onBackClick: () -> Unit = {}) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFF9C4),
                contentColor = Color.Black
            ) {
                IconButton(onClick = onBackClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Settings, contentDescription = "")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("", fontSize = 12.sp)
                }
                IconButton(onClick = {}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Share, contentDescription = "")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("", fontSize = 12.sp)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFDCEFFF))
                .padding(paddingValues)
        ) {
            // Encabezado amarillo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF9C4))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onPerfilClick) {
                        Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = Color.Black)
                    }

                    Text("DuocDesk", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen de proyecto
            Image(
                painter = painterResource(id = R.drawable.duoc_desk),
                contentDescription = "Imagen de DuocDesk",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Informacion
            Text(
                text = "demo@duocuc.cl",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tablero de trabajo DuocDesk. Software de gestión de tableros y tareas colaborativas para equipos de estudiantes.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Espacio visual
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Contenido adicional o vista previa de tareas")
            }
        }
    }
}
