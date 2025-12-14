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
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext
import com.example.duocdesk.util.SessionManager
import com.example.duocdesk.viewmodel.TableroViewModel

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    // Determinar el destino inicial basado en la sesión del usuario
    val startDestination = if (sessionManager.getUser() != null) "tablero" else "login"
    val navController = rememberNavController()
    val sharedTableroViewModel: TableroViewModel = viewModel()

    NavHost(
        navController = navController,
        // uso de la variable startDestination
        startDestination = startDestination,

        // 1. ANIMACIÓN DE ENTRADA 🔥🔥(Al navegar hacia un destino nuevo)
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start, // Entra desde la derecha
                animationSpec = tween(500) // Duración 500ms
            )
        },
        // 2. ANIMACIÓN DE SALIDA (La pantalla que se va)
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start, // Se va hacia la izquierda
                animationSpec = tween(500)
            )
        },
        // 3. ANIMACIÓN AL VOLVER (Pop Enter - La pantalla anterior reaparece)
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, // Entra desde la izquierda
                animationSpec = tween(500)
            )
        },
        // 4.  ANIMACIÓN AL VOLVER (Pop Exit - La pantalla actual se cierra)
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, // Se va hacia la derecha
                animationSpec = tween(500)
            )
        }
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
                tableroViewModel = sharedTableroViewModel,
                onPerfilClick = { navController.navigate("perfil") },
                onBuscarClick = { navController.navigate("buscar") },
                onFavoritosClick = { navController.navigate("favoritos") },
                onGitHubClick = { navController.navigate("github") },
                onFiltrarClick = {},
                onTableroClick = { tableroSeleccionado ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("tablero", tableroSeleccionado)
                    navController.navigate("detail")
                }
            )
        }




        // ------------------------
        // PERFIL
        // ------------------------
        composable("perfil") {
            PerfilScreen(
                onCloseClick = { navController.popBackStack() },
                onEditarPerfilClick = { navController.navigate("editar_perfil") },
                onLogout = {
                    com.example.duocdesk.model.UserSession.currentUser = null

                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ------------------------
        // EDITAR PERFIL
        // ------------------------
        composable("editar_perfil") {
            EditarPerfilScreen(
                onBack = {
                    navController.popBackStack()
                },
                onAccountDeleted = {
                    navController.navigate("login") {
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
                viewModel = sharedTableroViewModel,
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },

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
        composable("detail"){
            val tablero = navController.previousBackStackEntry?.savedStateHandle?.get<com.example.duocdesk.model.Tablero>("tablero")

            DetailScreen(
                tableroParam = tablero,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
