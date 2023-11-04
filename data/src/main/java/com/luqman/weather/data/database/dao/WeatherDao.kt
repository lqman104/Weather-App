package com.luqman.weather.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.luqman.weather.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE NOT date=:date")
    suspend fun getAllExceptCurrentDate(date: String): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather WHERE date=:date")
    suspend fun getAllByDate(date: String): Flow<List<WeatherEntity>>

    @Delete(WeatherEntity::class)
    suspend fun deleteAll()

}