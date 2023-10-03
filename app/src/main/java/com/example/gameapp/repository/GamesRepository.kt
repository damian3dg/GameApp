package com.example.gameapp.repository

import android.util.Log
import com.example.gameapp.data.ApiGames
import com.example.gameapp.model.GameList
import com.example.gameapp.model.GameModelFree
import com.example.gameapp.model.GamesModel
import com.example.gameapp.model.GamesModelScreenShoot
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

    suspend fun getGames(): List<GameList>? {
        //Llamamos a a la api games de la interface , la tenemos inyectada en el contructor, por ende no hace falta instanciarla
        val response = apiGames.getListGamesByDate()
        if (response.isSuccessful) {
            //Pedimos que sea tipo results por que es lo que devuelve la funcion
            return response.body()?.results
        }
        return null

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
}
