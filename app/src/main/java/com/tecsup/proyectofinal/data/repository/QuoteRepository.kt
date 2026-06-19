package com.tecsup.proyectofinal.data.repository

import com.tecsup.proyectofinal.data.remote.QuoteApi

class QuoteRepository(
    private val api: QuoteApi
) {

    suspend fun getQuote(): String {
        return try {
            val quote = api.getQuote()
            "${quote.quote}\n\n— ${quote.author}"
        } catch (e: Exception) {
            "No se pudo cargar la frase motivacional"
        }
    }
}