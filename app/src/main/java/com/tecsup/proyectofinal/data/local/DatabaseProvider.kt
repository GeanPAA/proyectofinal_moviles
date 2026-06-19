package com.tecsup.proyectofinal.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_db"
        ).allowMainThreadQueries()
            .build()
    }
}