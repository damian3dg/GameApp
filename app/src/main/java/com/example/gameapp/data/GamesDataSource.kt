package com.example.gameapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gameapp.model.GameList
import com.example.gameapp.model.GamesModel
import com.example.gameapp.repository.GamesRepository

class GamesDataSource(private val repo: GamesRepository, private val layout:String) : PagingSource<Int, GameList>() {
    override fun getRefreshKey(state: PagingState<Int, GameList>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, GameList> {
        return try {
            val nextPageNumber = params.key ?: 1

            val response:GamesModel

            if (layout == "HomeScreen"){
                response = repo.getGamesPaging(nextPageNumber, 5)
            } else {

                response = repo.getSearchGames(nextPageNumber, 5,layout)
            }
            Log.d("response",response.results.toString())
            LoadResult.Page(
                //data seria el dato recibido....
                data = response.results,
                //lo ponemos en null por que no hay pagina anterior
                prevKey = null,
                nextKey = if(response.results.isNotEmpty()) nextPageNumber + 1 else null

            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}