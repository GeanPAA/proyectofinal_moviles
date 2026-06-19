package com.tecsup.proyectofinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val titulo: String,

    val descripcion: String,

    val prioridad: String,

    val fechaLimite: String,

    val completada: Boolean = false
)