package com.example.weatherdi.retrofit

import com.example.weatherdi.model.ListWeather
import com.example.weatherdi.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("data/2.5/onecall")
    suspend fun get(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Response<ListWeather>
}