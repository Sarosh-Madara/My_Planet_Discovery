package com.compose.myplanetdiscovery.planet_edit.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.compose.myplanetdiscovery.model.PlanetModel
import com.compose.myplanetdiscovery.planet_edit.repository.AddEditPlanetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex


class AddEditPlanetScreenViewModelFactory(
    private val addEditPlanetRepository: AddEditPlanetRepository,
    private val planetId : Long?
) :
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddEditPlanetScreenViewModel(addEditPlanetRepository,planetId) as T
    }
}

class AddEditPlanetScreenViewModel(
    private val addEditPlanetRepository: AddEditPlanetRepository,
    private var planetID : Long?,
    private val autoSave : Boolean = true
) : ViewModel() {

    private val mutex = Mutex()

    private val _title by lazy { MutableStateFlow<String>("") }
    val title: StateFlow<String> by lazy { _title.asStateFlow() }

    private val _desc by lazy { MutableStateFlow<String>("") }
    val desc: StateFlow<String> by lazy { _desc.asStateFlow() }

    fun editTitle(title: String){
        _title.value = title
    }

    fun editDesc(desc: String){
        _desc.value = desc
    }



    fun savePlanet() = viewModelScope.launch {
        if (!mutex.isLocked) {

            val planet = PlanetModel(title = title.value, desc = desc.value, id = planetID ?: 0)

            if(!isNoteEmpty())
                planetID = addEditPlanetRepository.savePlanet(planetModel = planet)

        }
    }

    private fun isNoteEmpty(): Boolean {
        return _title.value.isBlank()
                && _desc.value.isBlank()
    }

}
