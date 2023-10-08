package com.example.gameapp.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gameapp.R
import com.example.gameapp.model.GameList
import com.example.gameapp.model.Platform
import com.example.gameapp.model.PlatformsItems
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.util.Constants.Companion.CUSTOM_GREEN
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit,
    onClickAction: () -> Unit
) {

    //Creamos un topBar generico, no se por que no se usa el scaffold , investigar
    TopAppBar(title = {
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp
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
    }, actions = {
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
fun CardGameCurrentWeek(game: GameList, from: String = "", onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(40.dp)
            .width(120.dp)
            .height(120.dp)
            .clickable { onClick() },

        ) {

        Box {
            if (game.background_image != null) {
                MainImage(image = game.background_image)
            } else {
                MainImage(image = "")
            }

            // Texto en la esquina inferior derecha
            Text(
                text = game.released,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Alinea el texto en la esquina inferior derecha
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun CardGamePopular(game: GameList, from: String = "", onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .shadow(40.dp)
            .width(180.dp)
            .height(180.dp)
            .clickable { onClick() },

        ) {

        Box {
            if (game.background_image != null) {
                MainImage(image = game.background_image)
            } else {
                MainImage(image = "")
            }

            // Texto en la esquina inferior derecha
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Alinea el cuadradito en la esquina superior izquierda
                    .background(Color.Green) // Color del cuadradito
                    .padding(4.dp), // Espaciado interno del cuadradito
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.metacritic.toString(), // Reemplaza "42" con el número de la API
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


@Composable
fun ImageDetail(image: String) {
    val imagenModificada = image.replace("/media/", "/media/crop/600/400/")

//    val painter = rememberAsyncImagePainter(image)
//    val state = painter.state

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imagenModificada).crossfade(true)
            .build(),
        contentDescription = "default crossfade example",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Crop


    )
}

@Composable
fun MainImage(image: String) {
    if (image != null) {
        //val imagenModificada = image.replace("/media/", "/media/crop/600/400/")
        val imagenModificada = image.replace("/media/", "/media/resize/420/-/")

        val painter = rememberAsyncImagePainter(imagenModificada)
        val state = painter.state


        AsyncImage(

            model = ImageRequest.Builder(LocalContext.current).data(imagenModificada)
                .memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED)
                .crossfade(true)

                .build(),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_launcher_background)


        )

    } else {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.ic_launcher_background).crossfade(true).build(),
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
fun MetaWebSite(url: String, release: String) {
    Log.d("release", release)
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Agrega el icono de reloj
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = Color.White,
            )

            // Agrega el texto del metascore
            Text(
                text = release,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
            )
        }
        Text(
            text = "METASCORE",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,

            )
        Button(
            onClick = { context.startActivity(intent) }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = Color.Gray
            )
        ) {
            Text(text = "Web")
        }

    }

}

@Composable
fun ReviewCard(metascore: Int) {
    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(CUSTOM_GREEN)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
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
fun Loader() {
    Row(Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(), color = Color.White
        )
    }

}

@Composable

fun PopularGames(
    popularGames: LazyPagingItems<GameList>, navController: NavController, pad: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(pad)
            .height(250.dp)
    ) {
        Row() {
            Text(
                text = "Popular",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(5.dp)
            )
        }
        LazyRow(
//            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(popularGames.itemCount) { index ->
                val item = popularGames[index]
                if (item != null) {

                    Column(Modifier.width(180.dp)) {
                        CardGamePopular(item) {
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
            when (popularGames.loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(12.dp)
                                .align(Alignment.CenterHorizontally)
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

@Composable

fun CurrentWeek(curretWeek: LazyPagingItems<GameList>, navController: NavController, pad: Dp) {
    Column(
        modifier = Modifier.padding(pad)
    ) {
        Row() {
            Text(
                text = "Current released Week",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(5.dp)
            )
        }
        LazyRow(
//            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(curretWeek.itemCount) { index ->
                val item = curretWeek[index]
                if (item != null) {

                    Column(Modifier.width(150.dp)) {
                        CardGameCurrentWeek(item) {
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
            when (curretWeek.loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(12.dp)
                                .align(Alignment.CenterHorizontally)
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


@Composable
fun TextDescription(text:String){
    var showMore by remember { mutableStateOf(false)
}

// Creating a long text

    Column(modifier = Modifier.padding(20.dp)) {

        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(500))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showMore = !showMore }) {

            if (showMore) {
                Text(text = text)
            } else {
                Text(text = text, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }

         }
    }

@Composable
fun PlatformList(item: List<PlatformsItems>) {
    //Creo una lista de nombres de plataforma
    val platformNames = item.map { it.platform.name }
    val commaSeparatedNames = platformNames.joinToString(", ")

    Column(Modifier.padding(15.dp)) {
        Text(text = "Platforms:", fontWeight = FontWeight.Bold)
        Text(text = commaSeparatedNames)
    }

}

