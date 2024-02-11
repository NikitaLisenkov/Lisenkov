package com.example.kinopoiskapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Films(
    @SerialName("films") val films: List<FilmPreviewRemote>
)

@Serializable
data class FilmPreviewRemote(
    @SerialName("filmId") val id: Int,
    @SerialName("nameRu") val nameRu: String? = null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("year") val year: String? = null,
    @SerialName("genres") val genres: List<Genre> = emptyList(),
    @SerialName("rating") val rating: String? = null,
    @SerialName("posterUrlPreview") val posterUrlPreview: String? = null,
)

@Serializable
data class Genre(
    @SerialName("genre") val genre: String? = null
)
