package com.example.weatherdi.model

data class Daily(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    val moon_phase: Float,
    val temp: Temp,
    val feels_like: Feels_like,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Float,
    val wind_speed: Float,
    val wind_deg: Int,
    val wind_gust: Float,
    val weather:List<Weather>,
    val clouds: Int,
    val pop: Float,
    val rain: Float,
    val uvi: Float
) {}