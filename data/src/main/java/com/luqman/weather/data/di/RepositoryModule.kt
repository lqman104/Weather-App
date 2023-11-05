package com.luqman.weather.data.di

import android.content.Context
import androidx.room.Room
import com.luqman.weather.data.database.WeatherDatabase
import com.luqman.weather.data.database.dao.WeatherDao
import com.luqman.weather.data.repository.LocalWeatherDataSource
import com.luqman.weather.data.repository.RemoteWeatherDataSource
import com.luqman.weather.data.repository.WeatherDataRepository
import com.luqman.weather.data.repository.WeatherDataSource
import com.luqman.weather.data.services.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(
        retrofit: Retrofit
    ): WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase = Room.databaseBuilder(
        context, WeatherDatabase::class.java, "weather_database"
    ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(
        database: WeatherDatabase
    ): WeatherDao = database.getWeatherDao()

    @Provides
    @LocalSource
    fun provideWeatherLocalDataSource(
        weatherDao: WeatherDao
    ): WeatherDataSource = LocalWeatherDataSource(weatherDao, Dispatchers.IO)

    @Provides
    @RemoteSource
    fun provideWeatherRemoteDataSource(
        weatherApiService: WeatherApiService
    ): WeatherDataSource = RemoteWeatherDataSource(
        weatherApiService,
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