package com.example.duocdesk.view

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.duocdesk.viewmodel.LoginViewModel
import com.example.duocdesk.viewmodel.RegisterViewModel
import com.example.duocdesk.viewmodel.GitHubViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ------------------------
        // LOGIN
        // ------------------------
        composable("login") {
            val vm: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = vm,
                onLoginSuccess = {
                    navController.navigate("tablero") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onCreateAccountClick = {
                    navController.navigate("register")
                }
            )
        }

        // ------------------------
        // REGISTER
        // ------------------------
        composable("register") {
            val vm: RegisterViewModel = viewModel()

            RegisterScreen(
                viewModel = vm,
                onRegisterSuccess = {
                    // 1. Volvemos al login
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // ------------------------
        // TABLERO
        // ------------------------
        composable("tablero") {
            TableroScreen(
                onPerfilClick = { navController.navigate("perfil") },
                onBuscarClick = { navController.navigate("buscar") },
                onFavoritosClick = { navController.navigate("favoritos") },
                onGitHubClick = { navController.navigate("github") },
                onFiltrarClick = {}
            )
        }

        // ------------------------
        // PERFIL
        // ------------------------
        composable("perfil") {
            PerfilScreen(
                onCloseClick = { navController.popBackStack() },
                onEditarPerfilClick = { navController.navigate("editar_perfil") }  // ← CORREGIDO
            )
        }

        // ------------------------
        // EDITAR PERFIL
        // ------------------------
        composable("editar_perfil") {
            EditarPerfilScreen(
                onBack = {
                    navController.navigate("login") {   // ← vuelve al login si se elimina
                        popUpTo("tablero") { inclusive = true }
                    }
                }
            )
        }

        // ------------------------
        // BUSCAR
        // ------------------------
        composable("buscar") {
            BuscarScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ------------------------
        // SEARCH RESULT
        // ------------------------
        composable("search_result") {
            SearchResultScreen(
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ------------------------
        // FAVORITOS
        // ------------------------
        composable("favoritos") {
            FavoritosScreen(
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ------------------------
        // GITHUB
        // ------------------------
        composable("github") {
            val gitVm: GitHubViewModel = viewModel()

            GitHubRepoScreen(
                vm = gitVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ------------------------
        // DETAIL
        // ------------------------
        composable("detail") {
            DetailScreen(
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}