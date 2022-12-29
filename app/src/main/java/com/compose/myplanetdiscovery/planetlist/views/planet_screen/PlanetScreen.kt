package com.compose.myplanetdiscovery.planetlist.views.planet_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.compose.myplanetdiscovery.planetlist.view_model.PlanetScreenUIState
import com.compose.myplanetdiscovery.planetlist.view_model.PlanetScreenViewModel
import com.compose.myplanetdiscovery.planetlist.views.planet_screen.ui_model.PlanetUIModel
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanetScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: PlanetScreenViewModel,
    systemUiController: SystemUiController,
    onEditNote: (id: Long) -> Unit,
    onCreateNewNote: () -> Unit,
) {

    val backgroundColor = MaterialTheme.colors.background

    SideEffect {
        systemUiController.setSystemBarsColor(backgroundColor, darkIcons = false)
    }

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadAllPlanets()
                systemUiController.setSystemBarsColor(backgroundColor, darkIcons = false)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NoteIt", fontWeight = FontWeight.Bold) },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                elevation = 0.dp
            )
        },
        floatingActionButton = {
            if (uiState is PlanetScreenUIState.LOADED) {
                FloatingActionButton(onClick = { onCreateNewNote() }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "New note"
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            when (uiState) {

                is PlanetScreenUIState.EMPTY -> {

                }

                is PlanetScreenUIState.LOADED -> {

                    val planets = uiState.planets

                    val padding = PaddingValues(all = 5.dp)


                    LazyColumn(content = {
                        items (planets.size){index ->
                            Planet(planets[index],padding){

                            }
                        }

                    })

                }

                is PlanetScreenUIState.LOADING -> {
                    Loading()
                }

                is PlanetScreenUIState.NoPlanet -> {
                    NoNotes { onCreateNewNote() }
                }
            }


        }
    }

}


@Composable
private fun NoNotes(onCreateNewNote: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "You have no saved notes. Create your first one by tapping the button below",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onCreateNewNote) {
                Text(text = "Create new note")
            }
        }

    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Planet(planet: PlanetUIModel, paddingValues: PaddingValues, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(0.5f)

            .clickable { onClick() }
    ) {
        Column {
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {

                Text(
                    planet.title,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (planet.desc != null) {
                    Text(
                        planet.desc, maxLines = 5, overflow = TextOverflow.Ellipsis,
                        lineHeight = 22.sp,
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }
}
