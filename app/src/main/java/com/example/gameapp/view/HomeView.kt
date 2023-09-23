package com.example.gameapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.gameapp.components.CardGame
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: GamesViewModel, navController: NavController) {
    Scaffold(topBar = {
        MainTopBar(title = "Api Games") {

        }
    }) {
        ContentHomeView(viewModel, it,navController)
    }
}

//@Composable
//fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues) {
//    val games by viewModel.games.collectAsState()
//    LazyColumn(
//        modifier = Modifier
//            .padding(pad)
//            .background(Color(0xFF0E0D0D))
//    ) {
//        items(games) { item ->
//            CardGame(item) {
//
//            }
//            Text(
//                text = item.name,
//                fontWeight = FontWeight.ExtraBold,
//                color = Color.White,
//                modifier = Modifier.padding(start = 10.dp)
//            )
//        }
//    }
//}

@Composable
fun ContentHomeView(viewModel: GamesViewModel, pad: PaddingValues, navController: NavController) {
    val games by viewModel.games.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(pad).background(Color(0xFF0E0D0D))

    ) {
        items(games) { item ->
            Column {
                CardGame(item) {

                    navController.navigate("DetailView/${item.id}")
                }
                Text(
                    text = item.name,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}
