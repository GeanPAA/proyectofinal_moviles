package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.proyectofinal.model.Task
import com.tecsup.proyectofinal.ui.navigation.Routes
import com.tecsup.proyectofinal.viewmodel.TaskViewModel
import androidx.compose.material3.Checkbox

@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {

    val tasks = viewModel.tasks.collectAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.TASK_FORM)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Text(
                text = "📋 Mis Tareas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Routes.STATISTICS)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("📊 Ver Estadísticas")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (tasks.isEmpty()) {
                Text(
                    text = "No hay tareas aún",
                    modifier = Modifier.padding(16.dp)
                )
            } else {

                LazyColumn {
                    items(tasks) { task ->

                        TaskItem(
                            task = task,
                            viewModel = viewModel,
                            onClick = {
                                navController.navigate("${Routes.TASK_DETAIL}/${task.id}")
                            },
                            onDelete = {
                                viewModel.delete(task)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    viewModel: TaskViewModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // 🔥 CHECKBOX (ESTADO COMPLETADO)
            Checkbox(
                checked = task.completada,
                onCheckedChange = {
                    viewModel.toggleTask(task)
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            // 🔥 INFO DE LA TAREA
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick() }
            ) {
                Text(
                    text = task.titulo,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(text = task.prioridad)

                Text(text = task.fechaLimite)
            }

            // 🔥 DELETE
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar"
                )
            }
        }
    }
}