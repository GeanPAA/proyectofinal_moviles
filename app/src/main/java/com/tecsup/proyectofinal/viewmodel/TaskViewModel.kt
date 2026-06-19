package com.tecsup.proyectofinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.proyectofinal.data.repository.QuoteRepository
import com.tecsup.proyectofinal.data.repository.TaskRepository
import com.tecsup.proyectofinal.model.Task
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    // 📌 TASKS (ROOM)
    val tasks = repository.getTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // 📌 FRASE API
    private val _quote = MutableStateFlow("Cargando...")
    val quote: StateFlow<String> = _quote

    fun loadQuote() {
        viewModelScope.launch {
            try {
                val result = quoteRepository.getQuote()
                _quote.value = result
            } catch (e: Exception) {
                _quote.value = "Error al cargar frase"
            }
        }
    }

    // 📌 CRUD
    fun insert(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(completada = !task.completada))
        }
    }
}