package com.compose.myplanetdiscovery.planet_edit.views.add_edit_planet_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.compose.myplanetdiscovery.core.view.PlanetCustomTextField
import com.compose.myplanetdiscovery.planet_edit.view_model.AddEditPlanetScreenViewModel
import com.google.accompanist.systemuicontroller.SystemUiController


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditPlanetScreen(
    planetScreenViewModel: AddEditPlanetScreenViewModel,
    systemUiController: SystemUiController? = null,
    onDelete: () -> Unit,
    onBack: () -> Unit
) {

   val padding =  Modifier.padding(all = 20.dp)
    Scaffold(

        bottomBar = {},
        topBar = {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    text = "New Planet",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Filled.ArrowBack, "back")
                }
            },
            backgroundColor = Color.Blue,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp
        )
    }
    ){
        Box(modifier = Modifier.padding(it))
        {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                PlanetCustomTextField(text = planetScreenViewModel.title.collectAsState().value , hint = "Input Title", onValueChanged = { text ->
                    planetScreenViewModel.editTitle(text)
                })
                Spacer(modifier = Modifier.padding(all = 10.dp))
                PlanetCustomTextField(text = planetScreenViewModel.desc.collectAsState().value , hint = "Input Desc", onValueChanged = { text ->
                    planetScreenViewModel.editDesc(text)
                })
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    planetScreenViewModel.savePlanet()
                    onBack
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}