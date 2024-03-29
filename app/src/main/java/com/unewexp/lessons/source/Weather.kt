package com.unewexp.lessons.source

data class Weather(val weather: Array<Map<String, Any>>, val main: Map<String, Any>)

data class ErrorResponse(val cod: String, val message: String)
