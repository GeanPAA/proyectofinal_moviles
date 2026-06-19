package com.tecsup.proyectofinal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tecsup.proyectofinal.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}