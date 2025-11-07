package com.example.duocdesk.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.duocdesk.viewmodel.LoginViewModel
import com.example.duocdesk.viewmodel.RegisterViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

// Pantalla de Login
        composable("login") {
            // El VM se crea aquí. Como es un AndroidViewModel,
            // el sistema le pasa el 'Application' context automáticamente.
            val viewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("tablero") {
                        popUpTo("login") { inclusive = true } // Limpia la pila
                    }
                },
                onCreateAccountClick = {
                    navController.navigate("register")
                }
                // onRecoverPasswordClick se maneja por defecto
            )
        }

        // Pantalla de Registro
        composable("register") {
            val viewModel: RegisterViewModel = viewModel()

            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    // Vuelve a login después del registro
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackToLoginClick = {
                    navController.popBackStack() // Vuelve atrás
                }
            )
        }

        // Pantalla del Tablero
        composable("tablero") {
            TableroScreen(
                onPerfilClick = { navController.navigate("perfil") },
                onBuscarClick = { navController.navigate("buscar") },
                onFavoritosClick = { navController.navigate("favoritos") },
                onFiltrarClick = { /* más adelante se puede agregar filtros */ }
            )
        }

        // Pantalla del Perfil
        composable("perfil") {
            PerfilScreen(
                onCloseClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de busqueda
        composable("buscar") {
            BuscarScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        // Resultados de busqueda
        composable("search_result") {
            SearchResultScreen(
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Favoritos
        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // Detalle de tablero
        composable("detail") {
            DetailScreen(
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
