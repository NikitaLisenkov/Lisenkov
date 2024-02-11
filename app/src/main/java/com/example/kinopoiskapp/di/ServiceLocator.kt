package com.example.kinopoiskapp.di

import com.example.kinopoiskapp.data.database.FilmsDataBase
import com.example.kinopoiskapp.data.network.FilmsApi
import com.example.kinopoiskapp.data.network.NetworkService
import com.example.kinopoiskapp.data.repository.FilmsRepository
import com.example.kinopoiskapp.domain.IFilmsRepository

object ServiceLocator {

    private val db: FilmsDataBase = FilmsDataBase.getDatabase()

    private val api: FilmsApi = NetworkService.api

    val repository: IFilmsRepository = FilmsRepository(db, api)

}