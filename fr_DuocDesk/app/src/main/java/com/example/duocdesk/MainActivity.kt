package com.example.duocdesk


import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.RecyclerView
import com.example.duocdesk.ui.theme.MVVMMaterialAppTheme
import com.example.duocdesk.view.WelcomeScreen
import com.example.duocdesk.viewmodel.UsuarioViewModel
import com.example.duocdesk.viewmodel.WelcomeViewModel

import androidx.activity.compose.setContent
import com.example.duocdesk.ui.theme.MVVMMaterialAppTheme
import com.example.duocdesk.view.LoginScreen
// 1. Importa tu NUEVA pantalla
import com.example.duocdesk.view.UserListScreen

// Ya no necesitas 'UsuarioViewModel' aquí, ni 'RecyclerView', 'ProgressBar', etc.
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. setContent es el punto de entrada de Compose
        setContent {
            // Configura tu tema personalizado
            MVVMMaterialAppTheme {
                // 3. Llama a tu nueva pantalla Composable.
                // ¡Y eso es todo!
                LoginScreen()
            }
        }
    }

    // Todos los métodos antiguos (setupRecyclerView, observeViewModel)
    // se pueden eliminar. La lógica ahora vive en UserListScreen.kt
}