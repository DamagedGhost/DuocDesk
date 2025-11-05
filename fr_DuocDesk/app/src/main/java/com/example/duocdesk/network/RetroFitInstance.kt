package com.example.duocdesk.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Reemplaza con la IP de tu PC y el puerto de Spring Boot (normalmente 8080)
    // Ejemplo: "http://192.168.1.100:8080/"
    private const val BASE_URL = "http://192.168.100.15:8080/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}