package com.example.gameapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gameapp.model.GamesModel
import com.example.gameapp.model.GamesModelScreenShoot
import com.example.gameapp.model.SingleGameModel
import com.example.gameapp.util.Constants.Companion.API_KEY
import com.example.gameapp.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiGames {

    //Se crean las llamadas a la api key desde una interfaz

    @GET(ENDPOINT + API_KEY)
    suspend fun getTopGames(
        @Query("page_size") pageSize: Int = 4,
        @Query("ordering") ordering: String = "-added",
        @Query("dates") dates: String = "2023-01-01,2024-12-31"
    ): Response<GamesModel>

    @GET(ENDPOINT + API_KEY)
    suspend fun getGamesPaging(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("ordering") ordering: String = "-added",
        @Query("dates") dates: String = "2022-01-01,2024-12-31"
    ): GamesModel


    @GET(ENDPOINT + API_KEY)
    suspend fun getListGamesByName(
        @Query("page") page: Int ,
        @Query("page_size") pageSize: Int,
        @Query(value = "search") search: String,
        @Query("ordering") ordering: String,
        @Query("search_exact") searchPrecise: Boolean,
    ): GamesModel

    @RequiresApi(Build.VERSION_CODES.O)
    @GET(ENDPOINT + API_KEY)
    suspend fun getListGamesByDate(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("dates") dates: String

    ): GamesModel

    //Es el equivalente a esto https://api.rawg.io/api/games/1123?key=eb61c283c59445a2b1dd68285981dbda
    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id") id: Int): Response<SingleGameModel>

    @GET("$ENDPOINT/{game_pk}/screenshots$API_KEY")
    suspend fun getScreenshotsForGame(
        @Path(value = "game_pk") gamePk: String,
    ): Response<GamesModelScreenShoot>

}


