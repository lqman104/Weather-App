package com.luqman.weather.data.di

import com.luqman.weather.data.repository.WeatherDataSource
import com.luqman.weather.data.repository.LocalWeatherDataSource
import com.luqman.weather.data.repository.WeatherDataRepository
import com.luqman.weather.data.repository.RemoteWeatherDataSource
import com.luqman.weather.data.services.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    @LocalSource
    fun provideWeatherLocalDataSource(): WeatherDataSource = LocalWeatherDataSource(
        Dispatchers.IO
    )

    @Provides
    @RemoteSource
    fun provideWeatherRemoteDataSource(
        weatherService: WeatherService
    ): WeatherDataSource = RemoteWeatherDataSource(
        weatherService,
        Dispatchers.IO
    )

    @Provides
    fun provideWeatherRepository(
        @RemoteSource remoteWeatherDataSource: WeatherDataSource,
        @LocalSource localWeatherDataSource: WeatherDataSource
    ): WeatherDataSource = WeatherDataRepository(
        remoteWeatherDataSource = remoteWeatherDataSource,
        localWeatherDataSource = localWeatherDataSource
    )
}