package com.unewexp.lessons.source

import com.unewexp.lessons.source.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {
    @GET("weather")
    suspend fun getData(@Query("q") city: String, @Query("appid") token: String): Response<Weather>
}