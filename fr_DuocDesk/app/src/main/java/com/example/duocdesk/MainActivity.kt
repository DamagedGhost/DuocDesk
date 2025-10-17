package com.example.duocdesk


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.duocdesk.ui.theme.MVVMMaterialAppTheme
import com.example.duocdesk.view.WelcomeScreen
import com.example.duocdesk.viewmodel.WelcomeViewModel

class MainActivity : ComponentActivity() {

    // Instancia del ViewModel utilizando la delegación viewModels()
    private val viewModel by viewModels<WelcomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de la pantalla de bienvenida (splash screen)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        // Configuración del contenido de la actividad utilizando Jetpack Compose
        setContent {
            MVVMMaterialAppTheme {
                WelcomeScreen()
            }
        }
    }
}