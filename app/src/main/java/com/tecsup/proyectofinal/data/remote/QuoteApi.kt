package com.tecsup.proyectofinal.data.remote

import retrofit2.http.GET

interface QuoteApi {

    @GET("quotes")
    suspend fun getQuote(): List<Quote>
}