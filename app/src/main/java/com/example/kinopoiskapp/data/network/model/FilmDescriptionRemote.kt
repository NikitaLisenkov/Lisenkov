package com.example.kinopoiskapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDescriptionRemote(
    @SerialName("kinopoiskId") val id: Int,
    @SerialName("nameRu") val nameRu: String? = null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("posterUrl") val posterUrl: String? = null,
    @SerialName("ratingKinopoisk") val rating: String? = null,
    @SerialName("year") val year: String? = null,
    @SerialName("filmLength") val filmLength: String? = null,
    @SerialName("slogan") val slogan: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("countries") val countries: List<Country> = emptyList(),
    @SerialName("genres") val genres: List<Genre> = emptyList(),
)

@Serializable
data class Country(
    @SerialName("country") val country: String? = null
)