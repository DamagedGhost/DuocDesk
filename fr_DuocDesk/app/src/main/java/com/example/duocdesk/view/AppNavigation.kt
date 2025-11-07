package com.example.duocdesk.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // Pantalla de Login
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password ->
                    navController.navigate("tablero")
                },
                onCreateAccountClick = {
                    navController.navigate("register")
                }
            )
        }

        // Pantalla de Registro
        composable("register") {
            RegisterScreen(
                onRegisterClick = { nombre, apellido, correo, password ->
                    navController.navigate("login")
                },
                onBackToLoginClick = {
                    navController.navigate("login")
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
