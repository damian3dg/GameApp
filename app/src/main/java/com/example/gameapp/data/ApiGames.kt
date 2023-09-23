package com.example.gameapp.data

import com.example.gameapp.model.GamesModel
import com.example.gameapp.model.SingleGameModel
import com.example.gameapp.util.Constants.Companion.API_KEY
import com.example.gameapp.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGames {

    //Se crean las llamadas a la api key desde una interfaz

    @GET(ENDPOINT + API_KEY)
    suspend fun getGames(): Response<GamesModel>

    //Es el equivalente a esto https://api.rawg.io/api/games/1123?key=eb61c283c59445a2b1dd68285981dbda
    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id") id: Int): Response<SingleGameModel>
}


