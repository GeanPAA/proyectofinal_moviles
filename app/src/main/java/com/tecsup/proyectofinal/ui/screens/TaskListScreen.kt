package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tecsup.proyectofinal.model.Task
import com.tecsup.proyectofinal.ui.navigation.Routes
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

// Elegant Theme Colors
private val DarkBg = Color(0xFF121212)
private val CardBg = Color(0xFF1E1E1E)
private val BeigeAccent = Color(0xFFD4B483)
private val SoftWhite = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    val tasks = viewModel.tasks.collectAsState().value

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBg,
                    titleContentColor = BeigeAccent
                ),
                title = {
                    Text(
                        "TASKFLOW",
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = BeigeAccent,
                contentColor = DarkBg,
                shape = CircleShape,
                onClick = { navController.navigate(Routes.TASK_FORM) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Header Section
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = "Welcome back,",
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftWhite.copy(alpha = 0.6f)
                )
                Text(
                    text = "Manage your day",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            }

            // Stats Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Task Overview", color = BeigeAccent, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${tasks.count { it.completada }}",
                                style = MaterialTheme.typography.headlineLarge,
                                color = SoftWhite,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = " / ${tasks.size}",
                                style = MaterialTheme.typography.titleMedium,
                                color = SoftWhite.copy(alpha = 0.5f)
                            )
                        }
                    }
                    Button(
                        onClick = { navController.navigate(Routes.STATISTICS) },
                        colors = ButtonDefaults.buttonColors(containerColor = BeigeAccent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Stats", color = DarkBg, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Task List Section
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No tasks scheduled yet",
                        color = SoftWhite.copy(alpha = 0.4f),
                        fontWeight = FontWeight.Light
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            viewModel = viewModel,
                            onClick = {
                                navController.navigate("${Routes.TASK_DETAIL}/${task.id}")
                            },
                            onDelete = { viewModel.delete(task) }
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
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.toggleTask(task) }) {
                if (task.completada) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = BeigeAccent,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(2.dp, SoftWhite.copy(alpha = 0.3f), CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (task.completada) SoftWhite.copy(alpha = 0.5f) else SoftWhite
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = when (task.prioridad.lowercase()) {
                                    "alta" -> Color(0xFFEF5350)
                                    "media" -> Color(0xFFFFCA28)
                                    else -> Color(0xFF66BB6A)
                                },
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = task.fechaLimite,
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftWhite.copy(alpha = 0.4f)
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFCF6679).copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
