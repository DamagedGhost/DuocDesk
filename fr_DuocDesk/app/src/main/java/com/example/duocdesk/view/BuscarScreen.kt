package com.example.duocdesk.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BuscarScreen(navController: NavController, onBackClick: () -> Unit = {}) {
    var busqueda by remember { mutableStateOf("") }

 Column(
     modifier = Modifier
         .fillMaxSize()
         .background(MaterialTheme.colorScheme.background)
         .padding(24.dp),
     horizontalAlignment = Alignment.CenterHorizontally
 ) {
     Spacer(modifier = Modifier.height(60.dp))

     Text(
         text = "Buenos dias [DamagedGhost],",
         fontSize = 22.sp,
         fontWeight = FontWeight.Bold
     )

     Text(
         text = "¿Que necesitas?",
         fontSize = 18.sp,
         color = MaterialTheme.colorScheme.onSurfaceVariant
     )

     Spacer(modifier = Modifier.height(20.dp))

     OutlinedTextField(
         value = busqueda,
         onValueChange = { busqueda = it },
         label = { Text("Buscar tablero...") },
         singleLine = true,
         modifier = Modifier.fillMaxWidth()
     )

     Spacer(modifier = Modifier.height(16.dp))

     Button(
         onClick = { navController.navigate("search_result") },
         modifier = Modifier.fillMaxWidth(),
         colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
     ) {
         Text("Buscar tablero", color = Color.White)
     }

     Spacer(modifier = Modifier.height(32.dp))

     TextButton(onClick = onBackClick) {
         Text("Volver al Tablero", color = MaterialTheme.colorScheme.tertiary)
     }
   }
}
