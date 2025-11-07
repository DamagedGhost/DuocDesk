package com.example.duocdesk.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SuccessDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        icon = {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Éxito",
                tint = Color(0xFF008000) // Un color verde
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}

@Preview
@Composable
fun SuccessDialogPreview() {
    SuccessDialog(
        onDismiss = {},
        title = "Operación Exitosa",
        message = "La operación se ha completado con éxito."
    )
}