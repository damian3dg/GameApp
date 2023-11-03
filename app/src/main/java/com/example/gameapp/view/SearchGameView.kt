package com.example.gameapp.view


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGameView(viewModel: GamesViewModel, navController: NavController) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsState()

    viewModel.clearSearchListgames()

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        query = query,
        onQueryChange = {
            query = it
            viewModel.searchListGames(it)
        },
        onSearch = {
            active = false
            if (query.isNotEmpty()) {
                viewModel.fetchSearchGames(query)
                navController.navigate("GameSearching") {
                    popUpTo("SearchGameView") { inclusive = true }
                }
                viewModel.clearSearchListgames()
            }
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Search, contentDescription = ""
            )
        },
        trailingIcon = {
            androidx.compose.material3.Icon(imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.clickable { navController.popBackStack() })
        },


        ) {
        if (searchResults.isNotEmpty()) {

            LazyColumn() {
                items(searchResults.size) { index ->
                    val resultItem = searchResults[index]
                    Text(text = resultItem.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                navController.navigate("DetailView/${resultItem.id}") {
                                    popUpTo("SearchGameView") { inclusive = true }
                                }
                                viewModel.clearSearchListgames()
                            })
                }
            }
        }
    }


}





