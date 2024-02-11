package com.example.kinopoiskapp.ui.films

sealed class FilmsState {
    data object Loading : FilmsState()
    data object Error : FilmsState()
    data class Content(val items: List<FilmPreviewUi>) : FilmsState()
}

