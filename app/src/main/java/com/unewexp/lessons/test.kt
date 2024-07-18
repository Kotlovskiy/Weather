package com.unewexp.lessons

import com.unewexp.lessons.source.GetData
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

fun main() = runBlocking{

    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val converter = GsonConverterFactory.create()

    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(converter)
        .build()

        val api = retrofit.create(GetData::class.java)

        var city = "London"
        val responseCall = api.getData(city, "3c782e22e43055c7b03927e0a8bf03fd")
        println(city)


}