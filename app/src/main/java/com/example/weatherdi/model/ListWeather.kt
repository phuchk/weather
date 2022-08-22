package com.example.weatherdi.model

data class ListWeather(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val timezone_offset: Int,
    val current: Current,
    val minutely: List<Minutely>,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
) {}