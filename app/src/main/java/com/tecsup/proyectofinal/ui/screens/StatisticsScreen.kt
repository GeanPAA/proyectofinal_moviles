package com.tecsup.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
fun StatisticsScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    val tasks = viewModel.tasks.collectAsState().value
    val quote = viewModel.quote.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadQuote()
    }

    val total = tasks.size
    val completadas = tasks.count { it.completada }
    val pendientes = total - completadas
    val progressValue = if (total == 0) 0f else completadas.toFloat() / total.toFloat()

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
                        "STATISTICS",
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Your Metrics",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = SoftWhite
                    )
                    Text(
                        text = "Analyzing your performance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SoftWhite.copy(alpha = 0.5f)
                    )
                }
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = BeigeAccent,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Text(
                        text = "OVERVIEW",
                        style = MaterialTheme.typography.labelLarge,
                        color = BeigeAccent,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatItem("TOTAL", total.toString())
                        StatItem("DONE", completadas.toString())
                        StatItem("PENDING", pendientes.toString())
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    LinearProgressIndicator(
                        progress = { progressValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp),
                        color = BeigeAccent,
                        trackColor = SoftWhite.copy(alpha = 0.05f),
                        strokeCap = StrokeCap.Round
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daily Goal Progress",
                            style = MaterialTheme.typography.bodySmall,
                            color = SoftWhite.copy(alpha = 0.4f)
                        )
                        Text(
                            text = "${(progressValue * 100).toInt()}%",
                            style = MaterialTheme.typography.titleMedium,
                            color = BeigeAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Quote Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = androidx.compose.foundation.BorderStroke(1.dp, SoftWhite.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .background(BeigeAccent.copy(alpha = 0.03f))
                        .padding(28.dp)
                ) {
                    Text(
                        text = "INSIGHT",
                        style = MaterialTheme.typography.labelLarge,
                        color = BeigeAccent,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "\"$quote\"",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = SoftWhite,
                        lineHeight = 28.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column {
        Text(
            text = label, 
            style = MaterialTheme.typography.labelSmall, 
            color = SoftWhite.copy(alpha = 0.4f),
            letterSpacing = 0.5.sp
        )
        Text(
            text = value, 
            style = MaterialTheme.typography.headlineMedium, 
            color = SoftWhite, 
            fontWeight = FontWeight.ExtraBold
        )
    }
}
