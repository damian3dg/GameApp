package com.example.gameapp.model

data class GamesModel(
    val count: Int,
    val results:List<GameList>

)

data class GameModelFree(

    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String

)

data class GameList(
    val id: Int,
    val name: String,
    val background_image: String,
)


data class GamesModelScreenShoot(
    val count: Int,
    val results:List<ScreenShot>

)
data class ScreenShot(
    val id: Int,
    val image: String,
    val hidden: Boolean,
    val width: Int,
    val height: Int
)