package com.example.gameapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.gameapp.model.GameList

@Composable
fun CardGameFromSearch(game: GameList, from : String = "", onClick: () -> Unit) {


    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(40.dp)
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() },

        ) {
        Column {
            if (game.background_image != null){
                MainImage(image = game.background_image)
            }
            else{
                MainImage(image = "")
            }
        }
    }
}