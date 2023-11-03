package com.example.gameapp.view


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gameapp.components.ComingSoon
import com.example.gameapp.components.Loader
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.components.PopularGames
import com.example.gameapp.components.TopGames
import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: GamesViewModel, navController: NavController) {
    Scaffold(topBar = {
        MainTopBar(title = "Games Data Base", onClickBackButton = {}) {
            navController.navigate("SearchGameView")

        }
    }) {
        ContentHomeView(viewModel, it, navController)

    }
}

@Composable
fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues, navController: NavController) {
    val games by viewModel.games.collectAsState()
    val popularGames = viewModel.popularGames.collectAsLazyPagingItems()
    val comingSoon = viewModel.currentGamesWeek.collectAsLazyPagingItems()
    val scrollState = rememberScrollState()
    val errorConnection by viewModel.errorConnection.collectAsState()

    if (popularGames.itemCount < 1) {
        // Muestra el ProgressBar mientras se carga
        Row(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            if (!errorConnection) {
                Loader()
            } else {
                Toast.makeText(LocalContext.current, "Error,try again", Toast.LENGTH_LONG).show()
                Button(
                    onClick = {
                        viewModel.fetchTopGames()
                        viewModel.fetchListGame()
                    }
                ) {
                    Text("Reintentar")
                }

            }


        }


    } else {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            TopGames(games = games, pad, navController)
            Spacer(modifier = Modifier.padding(8.dp))
            PopularGames(popularGames, navController)
            Spacer(modifier = Modifier.padding(8.dp))
            ComingSoon(comingSoon, navController, 0.dp)
        }


    }
}

