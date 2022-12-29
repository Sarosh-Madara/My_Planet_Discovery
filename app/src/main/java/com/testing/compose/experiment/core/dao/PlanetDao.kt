package com.testing.compose.experiment.core.dao

import androidx.room.*
import com.compose.myplanetdiscovery.core.entity.Planet

@Dao
interface PlanetDao {
    @Transaction
    @Query("SELECT * FROM planet")
    suspend fun getPlanet() : List<Planet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlanet(planet: Planet): Long
}