package com.compose.myplanetdiscovery.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testing.compose.experiment.core.dao.PlanetDao
import com.compose.myplanetdiscovery.core.entity.Planet

@Database(entities = [Planet::class], version = 1, exportSchema = false)
abstract class PlanetDb : RoomDatabase() {
    abstract fun planetDao(): PlanetDao

    companion object {
        private var INSTANCE: PlanetDb? = null

        fun getInstance(context: Context): PlanetDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlanetDb::class.java, "planet_list_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}