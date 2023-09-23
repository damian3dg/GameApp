package com.example.gameapp.di

import com.example.gameapp.data.ApiGames
import com.example.gameapp.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides

    //Se retorna un retrofit

    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Va a inyectar toda la interfaz , antes le inyectamos retrofit
    @Singleton
    @Provides
    fun providesAPiGames(retrofit:Retrofit) :ApiGames {
        return retrofit.create(ApiGames::class.java)
    }
}