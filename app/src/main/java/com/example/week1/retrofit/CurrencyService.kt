package com.example.week1.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyService {
    @GET("{to_from}.min.json")
    fun getExchangeRate(@Path("to_from") to_from: String): Call<String>
}