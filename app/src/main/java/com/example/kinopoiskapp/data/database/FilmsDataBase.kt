package com.example.kinopoiskapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FilmEntity::class], version = 1, exportSchema = false)
abstract class FilmsDataBase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    companion object {
        private const val DB_NAME = "FilmsDataBase"
        const val FILM = "film"

        private var instance: FilmsDataBase? = null

        @Synchronized
        fun initInstance(context: Context) {
            if (instance == null)
                instance = Room.databaseBuilder(context.applicationContext, FilmsDataBase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
        }

        fun getDatabase(): FilmsDataBase = instance!!
    }
}