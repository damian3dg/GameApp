package com.example.gameapp.view


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGameView(viewModel: GamesViewModel, navController: NavController) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val games by viewModel.games.collectAsState()


    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            active = false
            if (query.isNotEmpty()) {
                viewModel.fetchSearchGames(query)
                navController.popBackStack()

            }
        },
        active = active,
        onActiveChange ={active = it},
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            androidx.compose.material3.Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            androidx.compose.material3.Icon(imageVector = Icons.Default.Close, contentDescription = "",
            modifier = Modifier.clickable { navController.popBackStack() })
        },


    ) {}

}





