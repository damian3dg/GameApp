package com.example.gameapp.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gameapp.components.MainImage
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(viewModel: GamesViewModel, navController: NavController, id: Int) {
//El unit va para que se ejecute cuando abrimos la vista

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getGameById(id)
    }

    Scaffold(
        topBar =
        {

            MainTopBar(title = viewModel.state.name, showBackButton = true) { navController.popBackStack() }
        }
    )
    {
        //Supongo que poniendo it , automaticamente coloca el compose debajo d la topbar
        ContentDetailView(it, viewModel = viewModel)
    }
}

@Composable
fun ContentDetailView(pad: PaddingValues, viewModel: GamesViewModel) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
    ) {
        MainImage(image = state.background_image)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            //El spaceBetween los separa uno a la izq y el otro a la derecha
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 5.dp)
        ) {


        }

    }
}


