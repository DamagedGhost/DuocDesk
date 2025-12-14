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
import com.example.duocdesk.view.AppNavigation
import com.example.duocdesk.view.TableroScreen

import com.example.duocdesk.view.UserListScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MVVMMaterialAppTheme {
                AppNavigation()
            }
        }
    }
}