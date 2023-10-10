package com.example.gameapp.model


import java.time.LocalDate

data class SingleGameModel(
    val name: String,
    val description_raw: String,
    val metacritic: Int,
    val website: String,
    val background_image: String,
    val released: String,
    val platforms : List<PlatformsItems>,
    val genres : List<Genres>,
    val rating : String
)

class Genres (
    val name : String
)


data class PlatformsItems(
    val platform : Platform,
    val released_at : String
)

data class Platform(
    val id : Int,
    val name : String
)
