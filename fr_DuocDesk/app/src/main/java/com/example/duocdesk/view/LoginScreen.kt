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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.R
import com.example.duocdesk.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}, // <-- Lambda para navegar
    onCreateAccountClick: () -> Unit = {},
    onRecoverPasswordClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel() // <-- Obtenemos el VM
) {
    // 1. Recogemos el estado (UiState) del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // 2. Si el login fue exitoso, mostramos el diálogo
    if (uiState.loginSuccess) {
        SuccessDialog(
            title = "¡Bienvenido!",
            message = "Inicio de sesión exitoso. Serás dirigido al tablero.",
            onDismiss = onLoginSuccess
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
                text = "Ingresar Cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            // 3. CAMPO CORREO
            OutlinedTextField(
                value = uiState.email, // <-- Conectado al VM
                onValueChange = viewModel::onEmailChange, // <-- Evento al VM
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.email != null || uiState.errores.general != null, // <-- Conectado al VM
                supportingText = {
                    AnimatedVisibility(uiState.errores.email != null) {
                        Text(uiState.errores.email ?: "")
                    }
                    // Mostramos el error general (ej. credenciales) aquí también
                    AnimatedVisibility(uiState.errores.general != null) {
                        Text(uiState.errores.general ?: "")
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // 4. CAMPO CONTRASEÑA
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

            // 5. BOTÓN DE LOGIN
            Button(
                onClick = viewModel::onLoginClick, // <-- Evento al VM
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading // Se deshabilita mientras carga
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text(text = "Iniciar Sesion", color = MaterialTheme.colorScheme.onPrimary)
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onCreateAccountClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Crear Cuenta")
            }
        }
    }
}