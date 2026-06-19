package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

private val DarkBg = Color(0xFF121212)
private val CardBg = Color(0xFF1E1E1E)
private val BeigeAccent = Color(0xFFD4B483)
private val SoftWhite = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: Int,
    viewModel: TaskViewModel
) {
    val tasks = viewModel.tasks.collectAsState().value
    val task = tasks.find { it.id == taskId }

    if (task == null) {
        Box(modifier = Modifier.fillMaxSize().background(DarkBg), contentAlignment = Alignment.Center) {
            Text("Task not found", color = SoftWhite)
        }
        return
    }

    var isEditing by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(task.titulo) }
    var priority by remember { mutableStateOf(task.prioridad) }
    var date by remember { mutableStateOf(task.fechaLimite) }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBg,
                    titleContentColor = BeigeAccent,
                    navigationIconContentColor = BeigeAccent,
                    actionIconContentColor = Color(0xFFCF6679)
                ),
                title = { Text("DETAILS", fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.delete(task)
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Column {
                Text(
                    text = "Refine the Goal",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
                Spacer(modifier = Modifier.height(8.dp))
                AssistChip(
                    onClick = { },
                    label = { Text(if (task.completada) "COMPLETED" else "IN PROGRESS") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (task.completada) BeigeAccent.copy(alpha = 0.1f) else Color.Transparent,
                        labelColor = if (task.completada) BeigeAccent else SoftWhite.copy(alpha = 0.5f)
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (task.completada) BeigeAccent else SoftWhite.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if (isEditing) {
                        ElegantTextField(value = title, onValueChange = { title = it }, label = "Title")
                        ElegantTextField(value = priority, onValueChange = { priority = it }, label = "Priority")
                        ElegantTextField(value = date, onValueChange = { date = it }, label = "Deadline")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                viewModel.update(task.copy(titulo = title, prioridad = priority, fechaLimite = date))
                                isEditing = false
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BeigeAccent),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("SAVE CHANGES", color = DarkBg, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        DetailItem(label = "Title", value = task.titulo)
                        DetailItem(label = "Priority", value = task.prioridad)
                        DetailItem(label = "Deadline", value = task.fechaLimite)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = { isEditing = true },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = BeigeAccent),
                            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("EDIT TASK", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = SoftWhite.copy(alpha = 0.4f))
        Text(text = value, style = MaterialTheme.typography.titleLarge, color = SoftWhite, fontWeight = FontWeight.Medium)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ElegantTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = SoftWhite.copy(alpha = 0.4f)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = SoftWhite,
            unfocusedTextColor = SoftWhite,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = BeigeAccent,
            focusedIndicatorColor = BeigeAccent,
            unfocusedIndicatorColor = SoftWhite.copy(alpha = 0.1f)
        ),
        singleLine = true
    )
}
