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
fun FavoritosScreen(
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
            // Encabezado superior
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

                    Text("Favoritos", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Has marcado 3 tableros",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tarjetas de favoritos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FavoritoCard(
                    titulo = "DuocDesk",
                    imagen = R.drawable.duoc_desk,
                    onClick = { navController.navigate("detail") }
                )

                FavoritoCard(
                    titulo = "InVET",
                    imagen = R.drawable.duoc_desk,
                    onClick = { navController.navigate("detail") }
                )

                FavoritoCard(
                    titulo = "Demo",
                    imagen = R.drawable.ic_launcher_foreground,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun FavoritoCard(titulo: String, imagen: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
