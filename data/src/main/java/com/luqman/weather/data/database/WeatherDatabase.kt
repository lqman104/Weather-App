package com.luqman.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luqman.weather.data.database.dao.CityDao
import com.luqman.weather.data.database.dao.WeatherDao
import com.luqman.weather.data.database.entity.CityEntity
import com.luqman.weather.data.database.entity.WeatherEntity
import com.luqman.weather.data.services.dto.City

@Database(
    entities = [WeatherEntity::class, CityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getCityDao(): CityDao
}