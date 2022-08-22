package com.example.weatherdi.di

import com.example.weatherdi.retrofit.ApiService
import com.example.weatherdi.retrofit.ApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {
    @Binds
    fun mainRepository(apiServiceImpl: ApiServiceImpl): ApiService
}