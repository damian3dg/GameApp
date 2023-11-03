package com.example.gameapp.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gameapp.data.GamesDataSource
import com.example.gameapp.model.GameList
import com.example.gameapp.model.PlatformsItems
import com.example.gameapp.model.ScreenShot
import com.example.gameapp.repository.GamesRepository
import com.example.gameapp.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val repo: GamesRepository) : ViewModel() {

    private val _errorConnection = MutableStateFlow(false)
    val errorConnection: StateFlow<Boolean> = _errorConnection.asStateFlow()

    //Que son los mutablestateflow
    private val _games = MutableStateFlow<List<GameList>>(emptyList())
    val games = _games.asStateFlow()

    private val _screenshots = MutableStateFlow<List<ScreenShot>>(emptyList())
    val screenshots = _screenshots.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    var state by mutableStateOf(GameState())
        private set

    private val searchGames: Flow<PagingData<GameList>>? = null
    var searchGamesNonNull: Flow<PagingData<GameList>> = searchGames ?: emptyFlow()

    //private var _popularGames: Flow<PagingData<GameList>>? = null
    var popularGames: Flow<PagingData<GameList>> = emptyFlow()

    //private var _currentGamesWeek: Flow<PagingData<GameList>>? = null
    var currentGamesWeek: Flow<PagingData<GameList>> = emptyFlow()

    private val _searchResults = MutableStateFlow<List<GameList>>(emptyList())
    val searchResults: StateFlow<List<GameList>> = _searchResults

    private var searchJob: Job? = null


    init {
        fetchTopGames()
        fetchListGame()
    }


    fun fetchListGame() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    popularGames = Pager(PagingConfig(pageSize = 5)) {
                        GamesDataSource(repo, "popularGames")
                    }.flow.cachedIn(viewModelScope)

                    currentGamesWeek = Pager(PagingConfig(pageSize = 5)) {
                        GamesDataSource(repo, "currentWeek")
                    }.flow.cachedIn(viewModelScope)

                    _errorConnection.value = false
                } catch (e: Exception) {
                    _errorConnection.value = true
                }

            }
        }


    }

//    private fun fetchGames() {
//
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                //Aca ejecutamos la funcion que esta dentro del repository sin instanciarla ya que esta inyectada
//                val result = repo.getGames()
//                //En caso de que no venga nada le ponemos una emptyList
//                _games.value = result ?: emptyList()
//
//            }
//
//        }
//    }

    fun clearSearchListgames() {
        _searchResults.value = emptyList()
    }

    fun searchListGames(name: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            withContext(Dispatchers.IO) {
                try {
                    if (name.isNotEmpty()) {

                        val result = repo.getSearchGames(1, 15, reemplazarEspaciosConGuionesBajos(name))

                        _searchResults.value = result.results
                    } else {
                        _searchResults.value = emptyList()
                    }
                } catch (e: Exception) {
                    Log.d("lista view", e.toString())
                }
            }
        }
    }

    fun fetchSearchGames(name: String) {

        searchGamesNonNull = Pager(PagingConfig(pageSize = 5)) {
            GamesDataSource(repo, name)
        }.flow.cachedIn(viewModelScope)


//         _isLoading.value = true
//        viewModelScope.launch {
//
//            withContext(Dispatchers.IO) {
//                try {
//                    val result = repo.getSearchGames(reemplazarEspaciosConGuionesBajos(name))
//
//                    _games.value = result ?: emptyList()
//
//
//
//                } catch (e: Exception) {
//                    // Manejar errores si es necesario
//                } finally {
//                    // Indicar que la carga ha terminado, ya sea con éxito o error
//                    _isLoading.value = false
//                }
//
//
//
//
//
//            }
//
//        }
    }

    //Parametro que estamos enviando de una vista a otra. Por que se pasa a otra clase lo que recibimos?
    @SuppressLint("SuspiciousIndentation")
    fun getGameById(id: Int) {
        _isLoading.value = true
//    fetchScreenshotsForGame(id.toString())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                try {
                    // Realizar la carga de datos
                    val result = repo.getGameById(id)
                    state = state.copy(
                        name = result?.name ?: "",
                        description_raw = result?.description_raw ?: "",
                        metacritic = result?.metacritic ?: 0,
                        background_image = result?.background_image ?: "",
                        website = result?.website ?: "sin web",
                        released = result?.released ?: "no avaible",
                        platforms = result?.platforms ?: emptyList<PlatformsItems>(),
                        genres = result?.genres ?: emptyList(),
                        rating = result?.rating ?: ""

                    )
                } catch (e: Exception) {
                    // Manejar errores si es necesario
                } finally {
                    // Indicar que la carga ha terminado, ya sea con éxito o error
                    _isLoading.value = false
                }

            }
        }
    }


    fun fetchScreenshotsForGame(gamePk: String) {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val screenshotsList = repo.getScreenshotsForGame(gamePk)
                if (screenshotsList != null) {
                    _screenshots.value = screenshotsList
                }
            }
        }
    }

    fun fetchTopGames() {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val games = repo.getTopGames()
                    if (games != null) {
                        _games.value = games
                        _errorConnection.value = false
                    }

                } catch (e: Exception) {
                    _errorConnection.value = true
                }
            }
        }
    }

    fun clean() {
        state = state.copy(
            name = "",
            description_raw = "",
            metacritic = 0,
            background_image = "",
            website = "",

            )


        _screenshots.value = emptyList()

    }

    fun reemplazarEspaciosConGuionesBajos(texto: String): String {
        return texto.replace(" ", "-")
    }

}