package com.example.duocdesk


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.duocdesk.ui.theme.MVVMMaterialAppTheme
import com.example.duocdesk.ui.theme.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMMaterialAppTheme {
                WelcomeScreen()
            }
        }
    }
}