package com.unewexp.lessons.model

import com.unewexp.lessons.source.GetData
import com.unewexp.lessons.source.Resource
import com.unewexp.lessons.source.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPIService: BaseRepo() {

    private var api: GetData
    private val token = "78b4fdb4a16e9c465b78f7f574a5bc54"

    init {
        api = createApi()
    }

    private fun createClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return client
    }

    private fun createApi(): GetData {

        val client = createClient()

        val converter = GsonConverterFactory.create()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(converter)
            .build()

        val api = retrofit.create(GetData::class.java)

        return api
    }

    suspend fun getWeather(city: String): Resource<Weather> {
        return safeApiCall { api.getData(city, token) }
    }

}