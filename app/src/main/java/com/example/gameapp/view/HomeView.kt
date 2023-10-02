package com.example.gameapp.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gameapp.components.CardGame
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: GamesViewModel, navController: NavController) {
    Scaffold(topBar = {
        MainTopBar(title = "Games Data Base", onClickBackButton = {}) {
            navController.navigate("SearchGameView")

        }
    }) {
        ContentHomeView(viewModel, it,navController)
        // ScreenshotsView("28026", viewModel)
    }
}

@Composable
fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues,navController: NavController) {
    val games by viewModel.games.collectAsState()

    val gamesFree by viewModel.gamesFree.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        // Muestra el ProgressBar mientras se carga

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            color = Color.White


        )


    } else {
        LazyColumn(

            modifier = Modifier
                .padding(pad)
                .background(Color(0xFF0E0D0D)),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(games) { item ->
                CardGame(item) {
                    navController.navigate("DetailView/${item.id}")
                }
                Text(
                    text = item.name,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
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
