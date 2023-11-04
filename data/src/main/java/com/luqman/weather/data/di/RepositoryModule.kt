package com.luqman.weather.data.di

import android.content.Context
import androidx.room.Room
import com.luqman.weather.data.database.WeatherDatabase
import com.luqman.weather.data.repository.LocalWeatherDataSource
import com.luqman.weather.data.repository.RemoteWeatherDataSource
import com.luqman.weather.data.repository.WeatherDataRepository
import com.luqman.weather.data.repository.WeatherDataSource
import com.luqman.weather.data.services.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase = Room.databaseBuilder(
        context, WeatherDatabase::class.java, "weather_database"
    ).build()

    @Provides
    @LocalSource
    fun provideWeatherLocalDataSource(
        database: WeatherDatabase
    ): WeatherDataSource = LocalWeatherDataSource(
        database,
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