package com.example.duocdesk.view

import com.example.duocdesk.view.GitHubRepoScreen
import com.example.duocdesk.viewmodel.GitHubViewModel

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.duocdesk.viewmodel.LoginViewModel
import com.example.duocdesk.viewmodel.RegisterViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // ───────────────────────────────────────────────
        // LOGIN
        // ───────────────────────────────────────────────
        composable("login",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            val viewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("tablero") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onCreateAccountClick = { navController.navigate("register") }
            )
        }

        // ───────────────────────────────────────────────
        // REGISTER
        // ───────────────────────────────────────────────
        composable("register",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            val viewModel: RegisterViewModel = viewModel()

            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackToLoginClick = { navController.popBackStack() }
            )
        }

        // ───────────────────────────────────────────────
        // TABLERO
        // ───────────────────────────────────────────────
        composable("tablero",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            TableroScreen(
                onPerfilClick = { navController.navigate("perfil") },
                onBuscarClick = { navController.navigate("buscar") { launchSingleTop = true } },
                onFavoritosClick = { navController.navigate("favoritos") { launchSingleTop = true } },
                onFiltrarClick = { /* futuro */ },
                onGitHubClick = { navController.navigate("github") }   // 🔥 AÑADIDO
            )
        }

        // ───────────────────────────────────────────────
        // PERFIL
        // ───────────────────────────────────────────────
        composable("perfil",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            PerfilScreen(
                onCloseClick = { navController.popBackStack() }
            )
        }

        // ───────────────────────────────────────────────
        // BUSCAR
        // ───────────────────────────────────────────────
        composable("buscar",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            BuscarScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ───────────────────────────────────────────────
        // RESULTADO DE BÚSQUEDA
        // ───────────────────────────────────────────────
        composable("search_result",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            SearchResultScreen(
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ───────────────────────────────────────────────
        // FAVORITOS
        // ───────────────────────────────────────────────
        composable("favoritos",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            FavoritosScreen(
                navController = navController,
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ───────────────────────────────────────────────
        // GITHUB SCREEN (NUEVO)
        // ───────────────────────────────────────────────
        composable("github",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            val gitVm: GitHubViewModel = viewModel()

            // 👇 SOLO el token, SIN "Bearer" ni "token"
            val token = "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"   // ej: "ghp_JqJic8TYD..."

            GitHubRepoScreen(
                token = token,
                vm = gitVm
            )
        }

        // ───────────────────────────────────────────────
        // DETAIL
        // ───────────────────────────────────────────────
        composable("detail",
            enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 1000 }) },
            exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -1000 }) },
            popEnterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { -1000 }) },
            popExitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            DetailScreen(
                onPerfilClick = { navController.navigate("perfil") },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
