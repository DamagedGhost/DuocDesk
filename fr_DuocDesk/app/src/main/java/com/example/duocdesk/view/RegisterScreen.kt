package com.example.duocdesk.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duocdesk.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {}, // <-- Lambda para navegar
    onBackToLoginClick: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel() // <-- Obtenemos el VM
) {
    // 1. Recogemos el estado (UiState) del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // 2. Si el registro fue exitoso, mostramos el diálogo
    if (uiState.registrationSuccess) {
        SuccessDialog(
            onDismiss = onRegisterSuccess, // Al cerrar, navegamos
            title = "¡Registro Exitoso!",
            message = "Tu cuenta ha sido creada. Ahora serás dirigido al Login."
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ... (Logo y Título se quedan igual) ...
            Image(
                painter = painterResource(id = R.drawable.duoc_desk),
                contentDescription = "Logo DuocDesk",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Crear Cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            // 3. CAMPO NOMBRE
            OutlinedTextField(
                value = uiState.nombre, // <-- Conectado al VM
                onValueChange = viewModel::onNombreChange, // <-- Evento al VM
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.nombre != null, // <-- Conectado al VM
                supportingText = { // <-- Muestra el error
                    AnimatedVisibility(uiState.errores.nombre != null) {
                        Text(uiState.errores.nombre ?: "")
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 4. CAMPO APELLIDO
            OutlinedTextField(
                value = uiState.apellido,
                onValueChange = viewModel::onApellidoChange,
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.apellido != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.apellido != null) {
                        Text(uiState.errores.apellido ?: "")
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 5. CAMPO CORREO
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.email != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.email != null) {
                        Text(uiState.errores.email ?: "")
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 6. CAMPO CONTRASEÑA
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.password != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.password != null) {
                        Text(uiState.errores.password ?: "")
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            // 7. BOTÓN DE REGISTRO
            Button(
                onClick = viewModel::onRegisterClick, // <-- Evento al VM
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading // Se deshabilita mientras carga
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text(text = "Registrarse", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onBackToLoginClick, enabled = !uiState.isLoading) {
                Text("¿Ya tienes una cuenta? Inicia sesión")
            }
        }
    }
}