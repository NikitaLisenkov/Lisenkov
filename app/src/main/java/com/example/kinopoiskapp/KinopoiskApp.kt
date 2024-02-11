package com.example.kinopoiskapp

import android.app.Application
import com.example.kinopoiskapp.data.database.FilmsDataBase

class KinopoiskApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FilmsDataBase.initInstance(applicationContext)
    }

}