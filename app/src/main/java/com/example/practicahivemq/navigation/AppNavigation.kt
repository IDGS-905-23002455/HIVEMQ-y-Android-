package com.example.practicahivemq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.practicahivemq.screens.AgregarLibroScreen
import com.example.practicahivemq.screens.EditarLibroScreen
import com.example.practicahivemq.screens.HomeScreen
import com.example.practicahivemq.viewmodel.LibroViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val vm: LibroViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {

            HomeScreen(
                navController,
                vm
            )

        }

        composable("agregar") {

            AgregarLibroScreen(
                navController,
                vm
            )

        }

        composable(
            route = "editar/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            EditarLibroScreen(
                navController = navController,
                vm = vm,
                id = id
            )

        }

    }

}