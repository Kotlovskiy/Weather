package com.unewexp.lessons.presenter

import com.unewexp.lessons.AppGraph
import com.unewexp.lessons.model.Client
import com.unewexp.lessons.model.RestAPIService
import javax.inject.Inject

class Presenter {
    @Inject
    lateinit var api: RestAPIService

    suspend fun getDescription(city: String): List<String> {
        val data = api.getWeather(city)
        val response =
            if (data.data != null) {
                listOf("S", data.data.weather[0].get("description").toString())
            } else {
                listOf("E", data.message!!)
            }
        return response

    }

    suspend fun getTemp(city: String): List<String>{
        val data = api.getWeather(city)
        val response =
            if (data.data != null) {
                listOf("S", (data.data.main.get("temp").toString().toFloat()-273).toInt().toString())
            } else {
                listOf("E", data.message!!)
            }
        return response
    }
}