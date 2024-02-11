package com.example.kinopoiskapp.data.repository

import com.example.kinopoiskapp.data.database.FilmEntity
import com.example.kinopoiskapp.data.network.model.FilmDescriptionRemote
import com.example.kinopoiskapp.data.network.model.FilmPreviewRemote
import com.example.kinopoiskapp.data.network.model.Genre
import com.example.kinopoiskapp.ui.film_details.FilmDescriptionUi
import com.example.kinopoiskapp.ui.films.FilmPreviewUi

object Mapper {

    private fun List<Genre>.toReadableGenre(): String = mapNotNull { it.genre }
        .mapIndexed { index, text ->
            if (index == 0) text.replaceFirstChar(Char::uppercaseChar) else text
        }.joinToString()

    fun FilmDescriptionRemote.toUi(isFavourite: Boolean): FilmDescriptionUi {
        return FilmDescriptionUi(
            id = id,
            nameRu = nameRu.orEmpty(),
            nameEn = nameEn.orEmpty(),
            posterUrl = posterUrl.orEmpty(),
            rating = rating.orEmpty(),
            year = year.orEmpty(),
            filmLength = filmLength.orEmpty(),
            slogan = slogan.orEmpty(),
            description = description.orEmpty(),
            countries = countries.mapNotNull { it.country },
            genres = genres.toReadableGenre(),
            favourite = isFavourite
        )
    }

    fun FilmPreviewRemote.toUi(): FilmPreviewUi {
        val nameEn = when {
            nameEn.isNullOrBlank() -> year.orEmpty()
            else -> "$nameEn, $year"
        }

        return FilmPreviewUi(
            id = id,
            nameRu = nameRu.orEmpty(),
            nameEn = nameEn,
            year = year.orEmpty(),
            genres = genres.toReadableGenre(),
            rating = rating.orEmpty(),
            posterUrlPreview = posterUrlPreview.orEmpty(),
            favourite = false
        )
    }

    fun FilmPreviewUi.toEntity(): FilmEntity {
        return FilmEntity(
            id = id,
            nameRu = nameRu,
            nameEn = nameEn,
            year = year,
            genres = genres,
            rating = rating,
            posterUrlPreview = posterUrlPreview,
            favourite = favourite
        )
    }

    fun FilmEntity.toUi(): FilmPreviewUi {
        return FilmPreviewUi(
            id = id,
            nameRu = nameRu,
            nameEn = nameEn,
            year = year,
            genres = genres,
            rating = rating,
            posterUrlPreview = posterUrlPreview,
            favourite = favourite
        )
    }

}