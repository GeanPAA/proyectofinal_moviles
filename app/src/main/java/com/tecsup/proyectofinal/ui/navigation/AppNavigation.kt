package com.tecsup.proyectofinal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.tecsup.proyectofinal.ui.screens.*
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

@Composable
fun AppNavigation(viewModel: TaskViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {

        composable(Routes.TASK_LIST) {
            TaskListScreen(navController, viewModel)
        }

        composable(Routes.TASK_FORM) {
            TaskFormScreen(navController, viewModel)
        }

        composable(
            route = "${Routes.TASK_DETAIL}/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("taskId") ?: 0

            TaskDetailScreen(navController, id, viewModel)
        }

        composable(Routes.STATISTICS) {
            StatisticsScreen(navController, viewModel)
        }
    }
}