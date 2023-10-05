package com.example.gameapp.model

data class GamesModel(
    val count: Int,
    val results:List<GameList>

)



data class GameList(
    val id: Int,
    val name: String,
    val background_image: String,
    val metacritic:Int,
    val released: String
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