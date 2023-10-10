package com.example.gameapp.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gameapp.data.ApiGames
import com.example.gameapp.model.GameList
import com.example.gameapp.model.GamesModel
import com.example.gameapp.model.ScreenShot
import com.example.gameapp.model.SingleGameModel
import javax.inject.Inject

//Cual es la funcion del repository? Crear metodos que se van a usar en el viewModel. Se le pasa como parametro la interface
class GamesRepository @Inject constructor(private val apiGames: ApiGames) {
    //Por lo visto el repository es quien se encarga de realizar las llamadas a la api
    //Aca se llama a GameList, por que necesito los datos que vienen de result solamente
    suspend fun getSearchGames(page:Int, pageSize:Int,name:String,): GamesModel {

        return apiGames.getListGamesByName(page,pageSize,name,"-rating",true)


    }

    suspend fun getGamesPaging(page:Int, pageSize:Int):GamesModel{
        return apiGames.getGamesPaging(page,pageSize)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesByDate(page: Int, pageSize: Int, getCurrentWeekDates: String): GamesModel {
        //Llamamos a a la api games de la interface , la tenemos inyectada en el contructor, por ende no hace falta instanciarla
        return apiGames.getListGamesByDate(page,pageSize,getCurrentWeekDates)

    }


        suspend fun getGameById(id:Int):SingleGameModel? {
        val response = apiGames.getGameById(id)
        if(response.isSuccessful){
            Log.d("Api games",response.body().toString())
            return response.body()
        }
        return null
    }


    suspend fun getScreenshotsForGame(gamePk: String): List<ScreenShot>? {
        val response = apiGames.getScreenshotsForGame(gamePk)

        if(response.isSuccessful){
            return response.body()?.results
        }
        return null


    }

    suspend fun getTopGames(): List<GameList>? {
        val response = apiGames.getTopGames()

        if(response.isSuccessful){
            return response.body()?.results
        }
        return null


    }
}
