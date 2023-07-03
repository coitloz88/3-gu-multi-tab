package com.example.week1.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {
    @GET("krw/usd.min.json")
    fun getUsdToKrw(): Call<String>
}