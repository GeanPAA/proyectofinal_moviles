package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tecsup.proyectofinal.model.Task
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

private val DarkBg = Color(0xFF121212)
private val CardBg = Color(0xFF1E1E1E)
private val BeigeAccent = Color(0xFFD4B483)
private val SoftWhite = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBg,
                    titleContentColor = BeigeAccent,
                    navigationIconContentColor = BeigeAccent
                ),
                title = {
                    Text(
                        "NEW TASK",
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            Column {
                Text(
                    text = "Create Excellence",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
                Text(
                    text = "Define your next objective with precision",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftWhite.copy(alpha = 0.5f)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ElegantTextField(value = titulo, onValueChange = { titulo = it }, label = "Title")
                    ElegantTextField(value = descripcion, onValueChange = { descripcion = it }, label = "Description", isMultiline = true)
                    ElegantTextField(value = prioridad, onValueChange = { prioridad = it }, label = "Priority (Alta / Media / Baja)")
                    ElegantTextField(value = fecha, onValueChange = { fecha = it }, label = "Deadline")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (titulo.isNotBlank()) {
                        viewModel.insert(
                            Task(
                                titulo = titulo,
                                descripcion = descripcion,
                                prioridad = prioridad,
                                fechaLimite = fecha,
                                completada = false
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BeigeAccent),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "SAVE OBJECTIVE",
                    color = DarkBg,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElegantTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isMultiline: Boolean = false
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
        maxLines = if (isMultiline) 4 else 1,
        singleLine = !isMultiline
    )
}
