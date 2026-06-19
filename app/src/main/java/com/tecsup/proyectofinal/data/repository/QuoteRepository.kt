package com.tecsup.proyectofinal.data.repository

import com.tecsup.proyectofinal.data.remote.QuoteApi

class QuoteRepository(
    private val api: QuoteApi
) {

    suspend fun getQuote(): String {
        return try {
            val response = api.getQuote()
            val quote = response.firstOrNull()
            "${quote?.q ?: "Sin frase"} - ${quote?.a ?: ""}"
        } catch (e: Exception) {
            "Error al cargar frase"
        }
    }
}