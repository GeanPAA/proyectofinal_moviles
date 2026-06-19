package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {

    val tasks = viewModel.tasks.collectAsState().value
    val quote = viewModel.quote.collectAsState().value

    val total = tasks.size
    val completadas = tasks.count { it.completada }
    val pendientes = total - completadas

    // 🔥 CARGA API UNA VEZ
    LaunchedEffect(Unit) {
        viewModel.loadQuote()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("📊 Estadísticas", style = MaterialTheme.typography.headlineMedium)

        // 📊 CARD DE STATS
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total: $total")
                Text("Completadas: $completadas")
                Text("Pendientes: $pendientes")
            }
        }

        // 💡 CARD DE API
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("💡 Frase motivacional")
                Text(quote)
            }
        }
    }
}