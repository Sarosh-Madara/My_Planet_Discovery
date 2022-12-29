package com.compose.myplanetdiscovery.planet_edit.repository

import com.compose.myplanetdiscovery.core.entity.Planet
import com.compose.myplanetdiscovery.model.PlanetModel

interface AddEditPlanetRepository {
    suspend fun savePlanet(planetModel: PlanetModel): Long
}