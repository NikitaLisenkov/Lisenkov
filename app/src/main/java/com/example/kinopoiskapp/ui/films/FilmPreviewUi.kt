package com.example.kinopoiskapp.ui.films

data class FilmPreviewUi(
    val id: Int,
    val nameRu: String,
    val nameEn: String,
    val year: String,
    val genres: String,
    val rating: String,
    val posterUrlPreview: String,
    val favourite: Boolean
)

