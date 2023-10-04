package com.example.gameapp.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gameapp.components.CardGame
import com.example.gameapp.components.CardGameFromSearch
import com.example.gameapp.components.Loader
import com.example.gameapp.model.GameList
import com.example.gameapp.viewModel.GamesViewModel

@Composable
fun GameSearching(viewModel: GamesViewModel, navController: NavController) {

    val searchGames = viewModel.searchGamesNonNull.collectAsLazyPagingItems()


    LazyColumn(
//            horizontalAlignment = Alignment.CenterHorizontally

    ) {
        items(searchGames.itemCount) { index ->
            val item = searchGames[index]
            if (item != null) {

                Column() {
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
        //cuando haya agregado datos
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






