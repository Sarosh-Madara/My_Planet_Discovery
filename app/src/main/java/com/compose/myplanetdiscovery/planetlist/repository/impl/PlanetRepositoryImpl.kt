package com.compose.myplanetdiscovery.planetlist.repository.impl

import com.testing.compose.experiment.core.dao.PlanetDao
import com.compose.myplanetdiscovery.core.entity.Planet
import com.compose.myplanetdiscovery.model.PlanetModel
import com.compose.myplanetdiscovery.planetlist.repository.PlanetRepository

class PlanetRepositoryImpl(private val planetDao: PlanetDao) : PlanetRepository {

    override suspend fun getPlanet(): List<PlanetModel> {
        return planetDao.getPlanet().map{
            PlanetModel(
                title = it.title,
                desc = it.desc,
                id = it.id
            )
        }
    }
}