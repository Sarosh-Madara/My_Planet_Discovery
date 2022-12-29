package com.compose.myplanetdiscovery.planet_edit.repository.impl

import com.compose.myplanetdiscovery.core.entity.Planet
import com.compose.myplanetdiscovery.model.PlanetModel
import com.compose.myplanetdiscovery.planet_edit.repository.AddEditPlanetRepository
import com.testing.compose.experiment.core.dao.PlanetDao

class AddEditPlanetRepositoryImpl(private val planetDao: PlanetDao) : AddEditPlanetRepository {

    override suspend fun savePlanet(planetModel: PlanetModel): Long {
        return planetDao.savePlanet(Planet(title = planetModel.title, desc = planetModel.desc))
    }

}