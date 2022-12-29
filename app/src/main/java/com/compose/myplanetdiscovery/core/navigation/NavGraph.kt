package com.compose.myplanetdiscovery.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.compose.myplanetdiscovery.core.db.PlanetDb
import com.compose.myplanetdiscovery.planet_edit.repository.impl.AddEditPlanetRepositoryImpl
import com.compose.myplanetdiscovery.planet_edit.view_model.AddEditPlanetScreenViewModelFactory
import com.compose.myplanetdiscovery.planet_edit.views.add_edit_planet_screen.AddEditPlanetScreen
import com.compose.myplanetdiscovery.planetlist.repository.impl.PlanetRepositoryImpl
import com.compose.myplanetdiscovery.planetlist.view_model.PlanetScreenViewModelFactory
import com.compose.myplanetdiscovery.planetlist.views.planet_screen.PlanetScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun SetupNavGraph(navController: NavHostController) {

    val context = LocalContext.current
    val planetDao = PlanetDb.getInstance(context).planetDao()
    val systemUiController = rememberSystemUiController()

    NavHost(navController = navController, startDestination = Screen.PlanetScreen.route) {

        composable(route = Screen.PlanetScreen.route) {

            PlanetScreen(
                systemUiController = systemUiController,
                viewModel = viewModel(
                    factory = PlanetScreenViewModelFactory(
                        PlanetRepositoryImpl(planetDao)
                    )
                ),
                onEditNote = {
                    navController.navigate(Screen.EditPlanetScreen.getFullPath(it.toString())) {
                        launchSingleTop = true
                    }
                },
                onCreateNewNote = {
                    navController.navigate(Screen.EditPlanetScreen.getFullPath(null)) {
                        launchSingleTop = true
                    }
                }
            )

        }

        composable(
            route = Screen.EditPlanetScreen.route,
            arguments = listOf(
                navArgument(Screen.EditPlanetScreen.argPlanetID) {
                    type = NavType.StringType; nullable = true; defaultValue = null
                },
            )
        ) { backStackEntry ->

            val planetID = backStackEntry.arguments?.getString(Screen.EditPlanetScreen.argPlanetID)

            AddEditPlanetScreen(
                viewModel(
                    factory = AddEditPlanetScreenViewModelFactory(
                          AddEditPlanetRepositoryImpl(planetDao),
                        planetId = planetID?.toLong(),
                    )
                ),

                systemUiController = systemUiController,
                onBack = {
                    navController.popBackStack()
                },
                onDelete = {
                    navController.popBackStack()
                }
            )


        }

    }
}