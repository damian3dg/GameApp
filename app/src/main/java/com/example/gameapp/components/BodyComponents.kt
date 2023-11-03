package com.example.gameapp.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.gameapp.R
import com.example.gameapp.model.GameList
import com.example.gameapp.model.Genres
import com.example.gameapp.model.PlatformsItems
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.util.Constants.Companion.CUSTOM_GREEN
import okhttp3.internal.wait

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

        modifier = Modifier

            .shadow(40.dp)
            .width(120.dp)

            .clickable { onClick() },

        ) {

        Box {
            if (game.background_image != null) {
                MainImage(image = game.background_image)
            } else {
                MainImage(image = "")
            }

            // Texto en la esquina inferior derecha

        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun CardGamePopular(game: GameList, from: String = "", onClick: () -> Unit) {

    Card(

        modifier = Modifier
            .padding(10.dp)
            .width(160.dp)
            .height(170.dp)
            .clickable { onClick() },

        ) {

        Box {
            if (game.background_image != null) {
                MainImage(image = game.background_image)
            } else {
                MainImage(image = "")
            }

            if (game.metacritic.toString() != "0") Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Alinea el cuadradito en la esquina superior izquierda
                    .background(
                        color = Color.Black.copy(alpha = 0.2f), // Establece el color de fondo con transparencia
                        shape = CircleShape
                    ) // Aplica una forma circular
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
fun ImageDetail(image: String, navController: NavController) {
    val imagenModificada = image.replace("/media/", "/media/crop/600/400/")

//    val painter = rememberAsyncImagePainter(image)
//    val state = painter.state

    Box {
        // Imagen principal
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imagenModificada)
                .crossfade(true).build(),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        // Ícono de flecha en la esquina superior izquierda
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .size(48.dp)
                .align(Alignment.TopStart)

        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color.White, shape = CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun MainImage(image: String) {
    if (image != null) {
        val imagenModificada = image.replace("/media/", "/media/crop/600/400/")
        //val imagenModificada = image.replace("/media/", "/media/resize/420/-/")

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

fun Title(name: String, rating: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.weight(1f) // Este Text ocupará todo el espacio disponible
        )

        Text(
            text = "$rating*",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            modifier = Modifier.padding(start = 15.dp) // Agrega un espacio a la izquierda para separarlos
        )
    }
}

@Composable
fun MetaWebSite(
    url: String, release: String, item: List<PlatformsItems>, itemGenres: List<Genres>
) {
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


//        Button(
//            onClick = { context.startActivity(intent) }, colors = ButtonDefaults.buttonColors(
//                contentColor = Color.White, containerColor = Color.Gray
//            )
//        ) {
//            Text(text = "Web")
//        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopGames(games: List<GameList>, pad: PaddingValues, navController: NavController) {

    val pageCount = games.count()

    val density = LocalDensity.current
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        games.count()
    }


    Box(
        Modifier
            .fillMaxWidth()
            .height(330.dp)
            .padding(pad)
    ) {

        if (games.isNotEmpty()) {


            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .height(230.dp),
                beyondBoundsPageCount = 2,
                //.background(Color.Red),

                pageSpacing = 15.dp,
                // contentPadding = PaddingValues(horizontal = 10.dp),
                //pageSize = PageSize.Fixed(290.dp)
            ) { page ->
                val game = games[page]
                val imagenModificada =
                    game.background_image.replace("/media/", "/media/resize/420/-/")
                val request: ImageRequest
                with(density) {
                    request = ImageRequest.Builder(context).data(imagenModificada)
                        .crossfade(enable = true).build()
                    context.imageLoader.enqueue(request)
                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("DetailView/${game.id}")

                    }) {

                    Box {

                        AsyncImage(
                            model = request,
                            contentDescription = "default crossfade example",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp),

                            contentScale = ContentScale.Crop,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.BottomStart)
                        ) {
                            Text(
                                text = game.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }

                        // Puedes usar Spacer para ajustar la posición del Text
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                }

            }
        }




        Row(
            Modifier
                .height(20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.White else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
            }


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
    popularGames: LazyPagingItems<GameList>, navController: NavController
) {
    Column(
        modifier = Modifier.wrapContentSize()

    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Popular",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "View all",
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        navController.navigate("PopularGames")
                    }
            )
        }
        LazyRow(
//            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(popularGames.itemCount) { index ->
                val item = popularGames[index]
                if (item != null) {

                    Column(
                        Modifier
                            .width(180.dp)
                            //.height(260.dp)
                            .wrapContentSize()
                    ) {
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

fun ComingSoon(curretWeek: LazyPagingItems<GameList>, navController: NavController, pad: Dp) {
    Column(
        modifier = Modifier
            .padding(pad)
            .height(450.dp)
    ) {
        Row() {
            Text(
                text = "Cooming Soon",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(5.dp)
            )
        }


        LazyColumn(
//            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(curretWeek.itemCount) { index ->
                val item = curretWeek[index]
                if (item != null) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(15.dp)
                            .clickable {
                                navController.navigate("DetailView/${item.id}")

                            }


                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .background(Color(0xFF171513))
                        ) {
                            CardGameCurrentWeek(item) {

                                navController.navigate("DetailView/${item.id}")


                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally,

                                ) {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,

                                    fontSize = 15.sp
                                )
                                Text(
                                    text = item.released,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,

                                    )
                            }

                        }
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
                        //        Text(text = "Error")
                    }
                }
            }
        }
    }
}


@Composable
fun TextDescription(text: String) {
    var showMore by remember {
        mutableStateOf(false)
    }

// Creating a long text

    Column(modifier = Modifier.padding(15.dp)) {
        Text(text = "Description:", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(300))
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { showMore = !showMore }) {

            if (showMore) {
                Text(text = text, fontSize = 14.sp)
            } else {
                Text(text = text, maxLines = 3, overflow = TextOverflow.Ellipsis, fontSize = 14.sp)
            }
        }

    }
}

@Composable
fun PlatformList(item: List<PlatformsItems>, itemGenres: List<Genres>, website: String) {
    //Creo una lista de nombres de plataforma

    val platformNames = item.map { it.platform.name }
    val listItemGenres = itemGenres.map { it.name }

    val commaSeparatedNames = platformNames.joinToString(", ")
    val commaSeparaGenres = listItemGenres.joinToString(", ")

    Column(Modifier.padding(15.dp)) {
        Text(text = "Platforms:", fontWeight = FontWeight.Bold)
        Text(text = commaSeparatedNames, fontSize = 14.sp)

        Spacer(Modifier.height(15.dp))

        Text(text = "Genres:", fontWeight = FontWeight.Bold)
        Text(text = commaSeparaGenres, fontSize = 14.sp)

        Spacer(Modifier.height(15.dp))

        Text(text = "Website:", fontWeight = FontWeight.Bold)
        Text(text = website, fontSize = 14.sp)
    }

}

