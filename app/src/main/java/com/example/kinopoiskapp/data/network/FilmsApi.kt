package com.example.kinopoiskapp.data.network

import com.example.kinopoiskapp.data.network.model.FilmDescriptionRemote
import com.example.kinopoiskapp.data.network.model.Films
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FilmsApi {

    @GET("top")
    suspend fun getTopFilms(
        @Query("type") type: String = TYPE_TOP_100_FILMS,
        @Query("page") page: Int = 1
    ): Films

    @GET("{id}")
    suspend fun getFilmInfo(@Path("id") id: Int): FilmDescriptionRemote

    private companion object {
        private const val TYPE_TOP_100_FILMS = "TOP_100_POPULAR_FILMS"
    }

}