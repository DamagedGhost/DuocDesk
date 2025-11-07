package com.example.duocdesk.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.duocdesk.R
import com.example.duocdesk.viewmodel.PerfilViewModel

@Composable
fun DetailScreen(onPerfilClick: () -> Unit = {}, onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    val viewModel: PerfilViewModel = viewModel()
    val photoUri by viewModel.photoUri.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSavedPhoto(context)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFF9C4),
                contentColor = Color.Black
            ) {
                IconButton(onClick = onBackClick, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configuración")
                }
                IconButton(onClick = {}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Share, contentDescription = "Compartir")
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
                            Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = Color.Black)
                        }
                    }

                    Text("DuocDesk", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.duoc_desk),
                contentDescription = "Imagen de DuocDesk",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
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
