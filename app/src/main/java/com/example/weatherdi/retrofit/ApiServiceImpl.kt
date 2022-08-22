package com.example.weatherdi.retrofit

import com.example.weatherdi.model.ListWeather
import com.example.weatherdi.model.User
import dagger.assisted.Assisted
import retrofit2.Response

class ApiServiceImpl(private val api: Api) : ApiService {
    override suspend fun get(
        lat: String,
        lon: String,
        lang: String,
        units: String,
        appid: String
    ): Response<ListWeather> {
        return api.get(lat, lon, lang, units, appid)
    }

}