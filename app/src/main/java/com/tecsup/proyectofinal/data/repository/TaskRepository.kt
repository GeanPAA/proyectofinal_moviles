package com.tecsup.proyectofinal.data.repository

import com.tecsup.proyectofinal.data.local.TaskDao
import com.tecsup.proyectofinal.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val dao: TaskDao
) {

    fun getTasks(): Flow<List<Task>> = dao.getAllTasks()

    suspend fun insert(task: Task) {
        dao.insert(task)
    }

    suspend fun update(task: Task) {
        dao.update(task)
    }

    suspend fun delete(task: Task) {
        dao.delete(task)
    }

    suspend fun getById(id: Int): Task? {
        return dao.getTaskById(id)
    }
}