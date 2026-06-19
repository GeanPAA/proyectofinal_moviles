package com.tecsup.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tecsup.proyectofinal.data.local.DatabaseProvider
import com.tecsup.proyectofinal.data.repository.TaskRepository
import com.tecsup.proyectofinal.data.repository.QuoteRepository
import com.tecsup.proyectofinal.data.remote.RetrofitInstance
import com.tecsup.proyectofinal.ui.navigation.AppNavigation
import com.tecsup.proyectofinal.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseProvider.provideDatabase(this)
        val taskRepository = TaskRepository(db.taskDao())

        val quoteRepository = QuoteRepository(RetrofitInstance.api)

        val viewModel = TaskViewModel(taskRepository, quoteRepository)

        setContent {
            AppNavigation(viewModel)
        }
    }
}