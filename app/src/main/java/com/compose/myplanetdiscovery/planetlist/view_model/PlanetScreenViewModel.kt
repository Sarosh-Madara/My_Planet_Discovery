package com.compose.myplanetdiscovery.planetlist.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.compose.myplanetdiscovery.planetlist.repository.PlanetRepository
import com.compose.myplanetdiscovery.planetlist.views.planet_screen.ui_model.PlanetUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PlanetScreenViewModelFactory(
    private val planetRepository: PlanetRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlanetScreenViewModel(planetRepository) as T
    }
}

sealed class PlanetScreenUIState {
    object EMPTY : PlanetScreenUIState()
    object LOADING : PlanetScreenUIState()
    object NoPlanet : PlanetScreenUIState()
    data class LOADED(val planets: List<PlanetUIModel>) : PlanetScreenUIState()
}

class PlanetScreenViewModel(
    private val planetRepository: PlanetRepository,
    private val loadPlanetsOnInit: Boolean = true
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlanetScreenUIState>(PlanetScreenUIState.EMPTY)
    val uiState: StateFlow<PlanetScreenUIState> = _uiState.asStateFlow()

    init {
        if (loadPlanetsOnInit)
            loadAllPlanets()
    }

    fun loadAllPlanets() {
        viewModelScope.launch {

            if (_uiState.value !is PlanetScreenUIState.LOADED) {
                _uiState.value = PlanetScreenUIState.LOADING
            }
            val allPlanets = planetRepository.getPlanet()
            if (allPlanets.isEmpty()) {
                _uiState.value = PlanetScreenUIState.NoPlanet
            } else {
                _uiState.value = PlanetScreenUIState.LOADED(
                    allPlanets.map {
                        PlanetUIModel(
                            it.id, it.title, it.desc
                        )
                    }
                )
            }
        }
    }

}