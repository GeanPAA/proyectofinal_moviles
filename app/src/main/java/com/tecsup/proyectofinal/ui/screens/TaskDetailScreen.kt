package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: Int,
    viewModel: TaskViewModel
) {

    val task by remember(taskId) {
        mutableStateOf(viewModel.tasks.value.find { it.id == taskId })
    }

    if (task == null) {
        Text("Tarea no encontrada")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Detalle de tarea", style = MaterialTheme.typography.headlineMedium)

        Text("Título: ${task!!.titulo}")
        Text("Descripción: ${task!!.descripcion}")
        Text("Prioridad: ${task!!.prioridad}")
        Text("Fecha límite: ${task!!.fechaLimite}")

        Button(
            onClick = {
                viewModel.delete(task!!)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar")
        }
    }
}