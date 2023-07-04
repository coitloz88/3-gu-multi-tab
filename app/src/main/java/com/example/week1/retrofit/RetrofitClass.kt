package com.example.week1.retrofit

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

// Singleton object of Retrofit Class
object RetrofitClass {
    private const val BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build()
        }
        return instance!!
    }
}