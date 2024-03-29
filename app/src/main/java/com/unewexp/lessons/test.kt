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

        var fall = "fff"
        val responseCall = api.getData("qqq", "78b4fdb4a16e9c465b78f7f574a5bc54")
        println(fall)


}