package com.example.duocdesk.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.duocdesk.R

@Composable
fun SearchResultScreen(
    navController: NavController,
    onPerfilClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFFF9C4),
                contentColor = Color.Black
            ) {
                IconButton(onClick = { navController.navigate("buscar") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                }
                IconButton(onClick = { navController.navigate("favoritos") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                }
                IconButton(onClick = {}, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
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
            // Encabezado
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

                    Text("Resultados", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de busqueda
            OutlinedTextField(
                value = "DuocDesk",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text("Buscar tablero...") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "2 Tableros encontrados",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tarjeta principal (DuocDesk)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { navController.navigate("detail") },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.duoc_desk),
                        contentDescription = "Imagen de DuocDesk",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("DuocDesk", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        "Aplicación móvil inspirada en Trello para la gestión de tableros y tareas en equipos de Duoc UC.",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta secundaria (Demo)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Imagen")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Demo", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
