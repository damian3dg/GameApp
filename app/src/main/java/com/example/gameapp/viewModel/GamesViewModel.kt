package com.example.gameapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameapp.model.GameList
import com.example.gameapp.repository.GamesRepository
import com.example.gameapp.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val repo: GamesRepository) : ViewModel() {

    //Que son los mutablestateflow
    private val _games = MutableStateFlow<List<GameList>>(emptyList())
    val games = _games.asStateFlow()

    var state by mutableStateOf(GameState())
        private set

    init {
        fetchGames()
    }

    private fun fetchGames() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //Aca ejecutamos la funcion que esta dentro del repository sin instanciarla ya que esta inyectada
                val result = repo.getGames()
                //En caso de que no venga nada le ponemos una emptyList
                _games.value = result ?: emptyList()
            }

        }
    }
//Parametro que estamos enviando de una vista a otra. Por que se pasa a otra clase lo que recibimos?
    fun getGameById(id:Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = repo.getGameById(id)
                state = state.copy(
                    name = result?.name ?: "",
                    description_raw = result?.description_raw ?: "",
                    metacritic = result?.metacritic ?: 111,
                    background_image = result?.background_image ?: "",
                    website = result?.website ?: "sin web",


                )
            }
        }
    }
}