package com.compose.myplanetdiscovery.core.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planet")
data class Planet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val desc: String
)
