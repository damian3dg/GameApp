package com.example.gameapp.model

import java.time.LocalDate

data class SingleGameModel(
    val name: String,
    val description_raw: String,
    val metacritic: Int,
    val website: String,
    val background_image: String,
    val released: String
)
