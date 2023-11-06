package com.luqman.weather.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luqman.weather.data.database.entity.CityEntity
import com.luqman.weather.data.database.entity.WeatherEntity

@Dao
interface CityDao {
    @Query("SELECT * FROM city WHERE name=:city LIMIT 1")
    suspend fun get(city: String? = null): CityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: CityEntity): Long

    @Query("DELETE FROM city WHERE id=:id")
    suspend fun delete(id: Int)

}