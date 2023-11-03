package com.example.gameapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gameapp.components.CardGameFromSearch
import com.example.gameapp.components.Loader
import com.example.gameapp.viewModel.GamesViewModel

@Composable
fun GamesPopular(viewModel: GamesViewModel, navController: NavController) {

    val searchGames = viewModel.popularGames.collectAsLazyPagingItems()
    if (searchGames.itemCount < 1) {
        // Show Loader when is loading

        Row(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Loader()
        }

    } else {
        LazyColumn(
//            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(searchGames.itemCount) { index ->
                val item = searchGames[index]
                if (item != null) {

                    Column {
                        CardGameFromSearch(item) {
                            navController.navigate("DetailView/${item.id}")
                        }
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 10.dp),
                            fontSize = 12.sp
                        )
                    }

                }

            }
            //when add data
            when (searchGames.loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)

                        ) {
                            Loader()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text(text = "Error")
                    }
                }
            }
        }
    }
}