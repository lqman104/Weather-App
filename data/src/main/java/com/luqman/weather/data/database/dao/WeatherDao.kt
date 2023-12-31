package com.luqman.weather.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luqman.weather.data.database.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE city=:city")
    suspend fun getAll(city: String? = null): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entities: List<WeatherEntity>)

    @Query("DELETE FROM weather")
    suspend fun deleteAll()

}