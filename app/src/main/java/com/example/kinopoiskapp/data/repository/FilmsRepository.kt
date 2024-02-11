package com.example.kinopoiskapp.data.repository

import com.example.kinopoiskapp.data.database.FilmsDataBase
import com.example.kinopoiskapp.data.database.FilmEntity
import com.example.kinopoiskapp.data.network.FilmsApi
import com.example.kinopoiskapp.data.network.model.FilmDescriptionRemote
import com.example.kinopoiskapp.data.network.model.FilmPreviewRemote
import com.example.kinopoiskapp.domain.IFilmsRepository

class FilmsRepository(
    private val db: FilmsDataBase,
    private val api: FilmsApi,
) : IFilmsRepository {

    override suspend fun getTopFilms(): List<FilmPreviewRemote> = api.getTopFilms().films

    override suspend fun getFilmInfo(id: Int): FilmDescriptionRemote = api.getFilmInfo(id)

    override suspend fun addToDb(film: FilmEntity) = db.filmDao().insertFilm(film)

    override suspend fun deleteFromDb(film: FilmEntity) = db.filmDao().deleteFilm(film)

    override suspend fun getAllFilmsFromDb(): List<FilmEntity> = db.filmDao().getAll()

}