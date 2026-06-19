package com.tecsup.proyectofinal.data.remote

import retrofit2.http.GET

interface QuoteApi {

    @GET("quotes/random")
    suspend fun getQuote(): Quote
}