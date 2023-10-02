package com.example.gameapp.view


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDefaults.color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.gameapp.R
import com.example.gameapp.components.MainImage
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.components.MetaWebSite
import com.example.gameapp.components.ReviewCard
import com.example.gameapp.model.ScreenShot
import com.example.gameapp.util.Constants.Companion.CUSTOM_BLACK
import com.example.gameapp.viewModel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(viewModel: GamesViewModel, navController: NavController, id: Int) {
//El unit va para que se ejecute cuando abrimos la vista

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchScreenshotsForGame(id.toString())

    }

    LaunchedEffect(Unit) {
        viewModel.getGameById(id)

    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clean()
        }
    }

    Scaffold(
        topBar =
        {

            MainTopBar(
                title = viewModel.state.name,

                showBackButton = true,
                onClickBackButton = { navController.popBackStack() },
                onClickAction = {})

        })
    {


        val isLoading by viewModel.isLoading.collectAsState()

        if (isLoading) {
            // Muestra el ProgressBar mientras se carga


            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                color = Color.White


            )


        } else {

            ContentDetailView(it, viewModel = viewModel, id)
        }
    }
}

@Composable
fun ContentDetailView(pad: PaddingValues, viewModel: GamesViewModel, id: Int) {

    val state = viewModel.state
    val screenshots by viewModel.screenshots.collectAsState(emptyList())



    Column(

        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
            .fillMaxSize()

    ) {
        //MainImage(image = state.background_image)
        ScreenshotsView(id.toString(), screenshots)

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            //El spaceBetween los separa uno a la izq y el otro a la derecha
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 5.dp)
        ) {

            MetaWebSite(state.website)
            ReviewCard(metascore = (state.metacritic))
        }
        val scroll = rememberScrollState(0)
        Text(
            text = state.description_raw,
            color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(15.dp, 15.dp, 10.dp)
                .verticalScroll(scroll),


            )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenshotsView(gamePk: String, screenshots: List<ScreenShot>) {


    val pageCount = screenshots.count()

    val density = LocalDensity.current
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        screenshots.count()
    }


    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp),

        ) {

        if (screenshots.isNotEmpty()) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(200.dp),

                //.background(Color.Red),

//                pageSpacing = 10.dp,
//                contentPadding = PaddingValues(horizontal = 65.dp)
                //  pageSize = PageSize.Fixed(250.dp)
            ) { page ->
                val screenshot = screenshots[page]
                val imagenModificada = screenshot.image.replace("/media/", "/media/crop/600/400/")
                val request: ImageRequest
                with(density) {
                    request = ImageRequest.Builder(context)
                        .data(imagenModificada)
                        .crossfade(enable = true)
                        .build()
                    context.imageLoader.enqueue(request)
                }
                Card {
                    RoundedCornerShape(10.dp)

                    AsyncImage(
                        model = request,
                        contentDescription = "default crossfade example",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,




                        )
                }
            }

            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.Yellow else Color.LightGray
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


}