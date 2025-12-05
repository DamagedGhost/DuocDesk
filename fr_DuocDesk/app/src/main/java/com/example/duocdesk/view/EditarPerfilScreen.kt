package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.viewmodel.PerfilEditViewModel

@Composable
fun EditarPerfilScreen(
    vm: PerfilEditViewModel = viewModel(),
    onBack: () -> Unit,
    onDeleteSuccess: () -> Unit //nuevo callback para ir al login
) {
    val usuario by vm.usuario.collectAsState()
    val mensaje by vm.mensaje.collectAsState()

    var nombre by remember { mutableStateOf(usuario?.nombre ?: "") }
    var apellido by remember { mutableStateOf(usuario?.apellido ?: "") }
    var carrera by remember { mutableStateOf(usuario?.carrera ?: "") }
    var edad by remember { mutableStateOf(usuario?.edad?.toString() ?: "") }

    // Estado para mostrar el diálogo de confirmación
    var showConfirmDelete by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.padding(24.dp)) {

            Text("Editar Perfil", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    vm.actualizarCampo(nombre = it)
                },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = apellido,
                onValueChange = {
                    apellido = it
                    vm.actualizarCampo(apellido = it)
                },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = carrera,
                onValueChange = {
                    carrera = it
                    vm.actualizarCampo(carrera = it)
                },
                label = { Text("Carrera") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edad,
                onValueChange = {
                    edad = it
                    vm.actualizarCampo(edad = it.toIntOrNull() ?: 0)
                },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { vm.guardarCambios() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }

            mensaje?.let {
                Spacer(Modifier.height(10.dp))
                Text(it, color = MaterialTheme.colorScheme.secondary)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("Volver")
            }

            // BOTÓN ROJO PARA ELIMINAR
            Button(
                onClick = { showConfirmDelete = true }, // ⚠ muestra diálogo
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar Cuenta", color = MaterialTheme.colorScheme.onError)
            }
        }
    }

    // DIÁLOGO DE CONFIRMACIÓN
    if (showConfirmDelete) {
        AlertDialog(
            onDismissRequest = { showConfirmDelete = false },
            title = { Text("¿Eliminar cuenta?") },
            text = { Text("Esta acción no se puede deshacer. ¿Deseas continuar?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDelete = false
                        vm.eliminarCuenta {
                            onDeleteSuccess()  // navegamos al login
                        }
                    }
                ) {
                    Text("Sí, eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDelete = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

