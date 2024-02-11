package com.example.kinopoiskapp.data.database

import androidx.room.*

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Delete
    suspend fun deleteFilm(film: FilmEntity)

    @Query("SELECT * FROM film")
    suspend fun getAll(): List<FilmEntity>

}