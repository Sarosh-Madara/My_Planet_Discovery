package com.compose.myplanetdiscovery.core.navigation

sealed class Screen(val route: String) {
    object PlanetScreen : Screen("planet_screen")

    object EditPlanetScreen : Screen("edit_planet_screen?id={id}") {

        val argPlanetID = "id"

        fun getFullPath(planetID: String?): String {
            return if (planetID == null) "edit_planet_screen"
            else "edit_planet_screen?id=$planetID"
        }


    }
}
