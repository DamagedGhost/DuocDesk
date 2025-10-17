package com.example.duocdesk.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.duocdesk.viewmodel.WelcomeViewModel

@Composable
fun WelcomeScreen(welcomeViewModel: WelcomeViewModel = viewModel()) {
    val message by welcomeViewModel.welcomeMessage

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = message.text,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            FloatingActionButton(
                onClick = {
                    welcomeViewModel.updateMessage("¡Has presionado el botón!")
                },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}

