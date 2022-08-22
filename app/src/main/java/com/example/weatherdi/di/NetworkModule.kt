package com.example.weatherdi.di

import com.example.weatherdi.retrofit.Api
import com.example.weatherdi.retrofit.ApiService
import com.example.weatherdi.retrofit.ApiServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit.Builder): Api {
        return retrofit.build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideApiService(api: Api): ApiService {
        return ApiServiceImpl(api)
    }
}