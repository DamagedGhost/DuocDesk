package com.example.duocdesk.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.duocdesk.R
import com.example.duocdesk.model.Usuario
import com.example.duocdesk.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onRegisterSuccess: () -> Unit = {},
    onBackToLoginClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // 🔵 Si el registro fue exitoso, mostramos diálogo y luego llamamos al callback
    if (uiState.registrationSuccess) {
        SuccessDialog(
            title = "¡Registro exitoso!",
            message = "Tu cuenta se ha creado correctamente.",
            onDismiss = onRegisterSuccess
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.duoc_desk),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Crear Cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(20.dp))

            // ---- NOMBRE ----
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.nombre != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.nombre != null) {
                        Text(uiState.errores.nombre ?: "")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            // ---- APELLIDO ----
            OutlinedTextField(
                value = uiState.apellido,
                onValueChange = viewModel::onApellidoChange,
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.apellido != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.apellido != null) {
                        Text(uiState.errores.apellido ?: "")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            // ---- EMAIL ----
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.errores.email != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.email != null) {
                        Text(uiState.errores.email ?: "")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            // ---- PASSWORD ----
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.errores.password != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.password != null) {
                        Text(uiState.errores.password ?: "")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            // ---- CARRERA ----
            OutlinedTextField(
                value = uiState.carrera,
                onValueChange = viewModel::onCarreraChange,
                label = { Text("Carrera") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.carrera != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.carrera != null) {
                        Text(uiState.errores.carrera ?: "")
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            // ---- EDAD ----
            OutlinedTextField(
                value = uiState.edad,
                onValueChange = viewModel::onEdadChange,
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Teclado numérico
                isError = uiState.errores.edad != null,
                supportingText = {
                    AnimatedVisibility(uiState.errores.edad != null) {
                        Text(uiState.errores.edad ?: "")
                    }
                }
            )


            Spacer(Modifier.height(20.dp))

            // ---- BOTÓN REGISTRARSE ----
            Button(
                onClick = {
                    viewModel.registrar(
                        Usuario(
                            nombre = uiState.nombre,
                            apellido = uiState.apellido,
                            email = uiState.email,
                            password = uiState.password,
                            carrera = uiState.carrera,
                            edad = uiState.edad.toIntOrNull() ?: 0,
                            rolGlobal = "USER"
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Registrarse")
                }
            }

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = onBackToLoginClick,
                enabled = !uiState.isLoading
            ) {
                Text("¿Ya tienes una cuenta? Inicia sesión")
            }

            // Error general (por ejemplo, correo ya registrado / error de red)
            AnimatedVisibility(uiState.errores.general != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = uiState.errores.general ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
