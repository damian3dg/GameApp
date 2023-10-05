package com.example.gameapp.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gameapp.components.CurrentWeek
import com.example.gameapp.components.Loader
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.components.PopularGames
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
        // ScreenshotsView("28026", viewModel)
    }
}

@Composable
fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues, navController: NavController) {
    //val games by viewModel.games.collectAsState()
    val popularGames = viewModel.popularGames.collectAsLazyPagingItems()
    val currentGamesWeek = viewModel.currentGamesWeek.collectAsLazyPagingItems()
    //val nextWeek = viewModel.popularGames.collectAsLazyPagingItems()


    if (popularGames.itemCount < 1) {
        // Muestra el ProgressBar mientras se carga

        Row(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Loader()
        }


    } else {
        Column {
            PopularGames(popularGames, navController, pad)
            Spacer(modifier = Modifier.padding(8.dp))
            CurrentWeek(currentGamesWeek, navController, 0.dp)
        }


    }
}


//@Composable
//fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues, navController: NavController) {
//    val games by viewModel.games.collectAsState()
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.padding(pad).background(Color(0xFF0E0D0D))
//
//    ) {
//        items(games) { item ->
//            Column {
//                CardGame(item) {
//
//                    navController.navigate("DetailView/${item.id}")
//                }
//                Text(
//                    text = item.name,
//                    fontWeight = FontWeight.ExtraBold,
//                    color = Color.White,
//                    modifier = Modifier.padding(start = 10.dp)
//                )
//            }
//        }
//    }
//}
