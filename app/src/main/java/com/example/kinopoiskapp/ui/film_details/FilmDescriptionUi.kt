package com.example.kinopoiskapp.ui.film_details


data class FilmDescriptionUi(
    val id: Int,
    val nameRu: String,
    val nameEn: String,
    val posterUrl: String,
    val rating: String,
    val year: String,
    val filmLength: String,
    val slogan: String,
    val description: String,
    val countries: List<String>,
    val genres: String,
    val favourite: Boolean
)
