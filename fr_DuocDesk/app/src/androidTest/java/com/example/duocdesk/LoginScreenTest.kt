package com.example.duocdesk

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.duocdesk.view.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun muestra_texto_iniciar_sesion() {
        rule.setContent {
            LoginScreen()
        }

        rule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }
}
