package com.compose.myplanetdiscovery.planetlist.repository

import com.compose.myplanetdiscovery.model.PlanetModel

interface PlanetRepository {
    suspend fun getPlanet() : List<PlanetModel>
}