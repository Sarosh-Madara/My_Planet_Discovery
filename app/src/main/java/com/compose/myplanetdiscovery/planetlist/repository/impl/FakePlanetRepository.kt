package com.compose.myplanetdiscovery.planetlist.repository.impl

import com.compose.myplanetdiscovery.core.entity.Planet
import com.compose.myplanetdiscovery.model.PlanetModel
import com.compose.myplanetdiscovery.planetlist.repository.PlanetRepository

class FakePlanetRepository(private val planets: List<PlanetModel>) : PlanetRepository {
    override suspend fun getPlanet(): List<PlanetModel> {
        return planets
    }
}