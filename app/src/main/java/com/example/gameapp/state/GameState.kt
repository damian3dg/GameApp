package com.example.gameapp.state

import java.time.LocalDate
import java.util.Date

data class GameState(
    val name: String = "",
    val description_raw: String = "",
    val metacritic: Int = 0,
    val website: String = "",
    val background_image: String = "",
    val released: String = ""
)
