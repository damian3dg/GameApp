package com.example.gameapp.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gameapp.R
import com.example.gameapp.model.GameList
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.util.Constants.Companion.CUSTOM_GREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String, showBackButton: Boolean = false, onClickBackButton: () -> Unit, onClickAction: () -> Unit){

    //Creamos un topBar generico, no se por que no se usa el scaffold , investigar
    TopAppBar(title = {
        Text(
            text = title, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp
        )
    }, colors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = Color(CUSTOM_BLACK)
    ), navigationIcon = {
        if (showBackButton) {
            IconButton(onClick = onClickBackButton) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    },
        actions = {
            if (!showBackButton) {
                IconButton(onClick = onClickAction) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }

        }

    )

}

@Composable
fun CardGame(game: GameList, onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(40.dp)
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

@Composable
fun MainImage(image: String) {
    if (image != null){
    val imagenModificada = image.replace("/media/", "/media/crop/600/400/")
    val painter = rememberAsyncImagePainter(imagenModificada)
    val state = painter.state


    AsyncImage(

        model = ImageRequest.Builder(LocalContext.current)
            .data(imagenModificada)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)

            .build(),
        contentDescription = "",
        modifier = Modifier
            .width(130.dp)
            .height(130.dp),
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.ic_launcher_background)


    )

     }else
    {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.ic_launcher_background)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,

        )

    }


//    Image(
//
//
//
//        painter = painter,
//        contentScale = ContentScale.Crop,
//
//        contentDescription = "custom transition based on painter state",
//        modifier = Modifier.fillMaxWidth().height(250.dp)
//
//    )


}

@Composable
fun MetaWebSite(url: String,release:String) {
    Log.d("release",release)
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    Column() {
        Text(
            text = "METASCORE",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp, 10.dp),
        )
        Button(
            onClick = { context.startActivity(intent) }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Gray
            )
        ) {
            Text(text = "Sitio Web")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Agrega el icono de reloj
            Icon(
                imageVector = Icons.Default.DateRange, // Puedes elegir otro icono si lo deseas
                contentDescription = null, // Puedes agregar una descripción si es necesario
                tint = Color.White, // Cambia el color del icono si es necesario
            )

            // Agrega el texto del metascore
            Text(
                text = release,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
            )
        }
    }

}

@Composable
fun ReviewCard(metascore: Int) {
    Card(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(CUSTOM_GREEN)
        )
    )
    {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = metascore.toString(),
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 50.sp

            )
        }
    }

}

@Composable
fun Loader(){
    Row(Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            color = Color.White
        )
    }

}

