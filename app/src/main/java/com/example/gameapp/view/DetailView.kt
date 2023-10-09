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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.gameapp.components.ImageDetail
import com.example.gameapp.components.MainTopBar
import com.example.gameapp.components.MetaWebSite
import com.example.gameapp.components.PlatformList
import com.example.gameapp.components.ReviewCard
import com.example.gameapp.components.TextDescription
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
    val scroll = rememberScrollState(0)

    Box(
        modifier = Modifier
            .padding(pad)
            .background(Color(CUSTOM_BLACK))
            .fillMaxSize()
            .verticalScroll(state = scroll, enabled = true)
    ) {
    ImageDetail(image = state.background_image)

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 215.dp)
            .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)) // Redondea la parte superior
            .background(Color(CUSTOM_BLACK))

    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            //El spaceBetween los separa uno a la izq y el otro a la derecha
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {

            MetaWebSite(state.website, state.released, state.name)
            ReviewCard(metascore = (state.metacritic))
        }


        TextDescription(state.description_raw)
        PlatformList(state.platforms)
        ScreenshotsView(id.toString(), screenshots)

         }
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

    Text(text = "ScreenShoots:", modifier = Modifier.padding(15.dp))
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

                beyondBoundsPageCount = 2,
                //.background(Color.Red),

                pageSpacing = 10.dp,
                contentPadding = PaddingValues(horizontal = 65.dp),
                pageSize = PageSize.Fixed(250.dp)
            ) { page ->
                val screenshot = screenshots[page]
                val imagenModificada = screenshot.image.replace("/media/", "/media/resize/420/-/")
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