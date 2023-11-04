package com.luqman.weather.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luqman.weather.data.database.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    suspend fun getAllByDate(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entities: List<WeatherEntity>)

    @Delete(WeatherEntity::class)
    suspend fun deleteAll()

}