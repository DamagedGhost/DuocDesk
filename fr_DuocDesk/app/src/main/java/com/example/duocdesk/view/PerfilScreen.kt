package com.example.duocdesk.view

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.duocdesk.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = viewModel(),
    onCloseClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val photoUri by viewModel.photoUri.collectAsState()

    var showCamera by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadSavedPhoto(context)
    }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(RequestPermission()) { granted ->
            if (granted) showCamera = true
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (showCamera) {
            CameraPreview { uri ->
                viewModel.updatePhoto(context, uri.toString())
                showCamera = false
            }
        } else {
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

                if (photoUri != null) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Perfil",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = "Abrir cámara")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Actualizar foto")
                }

                Spacer(modifier = Modifier.height(20.dp))
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

@Preview
@Composable
fun PerfilScreenPreview() {
    PerfilScreen()
}
