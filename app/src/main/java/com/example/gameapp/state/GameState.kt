package com.example.gameapp.state

import com.example.gameapp.model.Genres
import com.example.gameapp.model.PlatformsItems

data class GameState(
    val name: String = "",
    val description_raw: String = "",
    val metacritic: Int = 0,
    val website: String = "",
    val background_image: String = "",
    val released: String = "",
    val platforms: List<PlatformsItems> = emptyList(),
    val genres: List<Genres> = emptyList(),
    val rating : String = ""
)
