package com.example.duocdesk.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.duocdesk.model.UserSession
import com.example.duocdesk.viewmodel.TableroViewModel
import java.util.Calendar

@Composable
fun BuscarScreen(navController: NavController,
                 viewModel: TableroViewModel,
                 onBackClick: () -> Unit = {}) {
    //lee texto de busqueda del viewmodel
    val busqueda by viewModel.textoBusqueda.collectAsState()
    val focusManager = LocalFocusManager.current


    //obtiene usuario actual
    val usuario = UserSession.currentUser
    val nombre = usuario?.nombre ?: "Usuario"

    //saludo segun hora del dia
    val saludo = remember {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hora) {
            in 6..11 -> "Buenos días"
            in 12..19 -> "Buenas tardes"
            else -> "Buenas noches"
        }
    }

 Column(
     modifier = Modifier
         .fillMaxSize()
         .background(MaterialTheme.colorScheme.background)
         .padding(24.dp),
     horizontalAlignment = Alignment.CenterHorizontally
 ) {
     Spacer(modifier = Modifier.height(60.dp))

     Text(
         text = "$saludo $nombre,", // Ej: "Buenas tardes Marcelo,"
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
         onValueChange = { viewModel.onBusquedaChange(it) },
         label = { Text("Buscar tablero...") },
         singleLine = true,
         modifier = Modifier.fillMaxWidth(),
         keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
         keyboardActions = KeyboardActions(
             onSearch = {
                 focusManager.clearFocus()
                 navController.navigate("search_result")

            }
        )
     )

     Spacer(modifier = Modifier.height(16.dp))

     Button(
         onClick = { navController.navigate("search_result") },
         modifier = Modifier.fillMaxWidth(),
         colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
         enabled = busqueda.isNotBlank()
     ) {
         Text("Buscar tablero", color = Color.White)
     }

     Spacer(modifier = Modifier.height(32.dp))

     TextButton(onClick = onBackClick) {
         Text("Volver al Tablero", color = MaterialTheme.colorScheme.tertiary)
     }
   }
}
