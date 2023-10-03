package com.example.gameapp.data

import com.example.gameapp.model.GameModelFree
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
    suspend fun getGames(): Response<GamesModel>

    @GET("https://www.freetogame.com/api/games")
    suspend fun getGamesFree(): Response<List<GameModelFree>>

    @GET(ENDPOINT + API_KEY)
    suspend fun getListGamesByName(
        @Query(value = "search") search: String,
        @Query("ordering") ordering: String,
        @Query("search_exact") searchPrecise: Boolean,
    ): Response<GamesModel>

    @GET(ENDPOINT + API_KEY)
    suspend fun getListGamesByDate(
        @Query("dates") dates: String = "2018-01-01,2023-12-31",
        @Query("page_size") page_size: Int = 50,
    ): Response<GamesModel>

    //Es el equivalente a esto https://api.rawg.io/api/games/1123?key=eb61c283c59445a2b1dd68285981dbda
    @GET("$ENDPOINT/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id") id: Int): Response<SingleGameModel>

        @GET("$ENDPOINT/{game_pk}/screenshots$API_KEY")
        suspend fun getScreenshotsForGame(
            @Path(value ="game_pk") gamePk: String,
        ): Response<GamesModelScreenShoot>

}


