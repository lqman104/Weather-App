package com.luqman.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luqman.weather.data.database.dao.WeatherDao
import com.luqman.weather.data.database.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}