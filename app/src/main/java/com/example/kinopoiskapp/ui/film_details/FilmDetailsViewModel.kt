package com.example.kinopoiskapp.ui.film_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskapp.data.repository.Mapper.toUi
import com.example.kinopoiskapp.domain.IFilmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class FilmDetailsViewModel(private val repository: IFilmsRepository) : ViewModel() {

    private val _state: MutableStateFlow<FilmDetailsState> = MutableStateFlow(FilmDetailsState.Loading)
    val state: Flow<FilmDetailsState> = _state

    fun loadFilm(id: Int, isFavourite: Boolean) {
        viewModelScope.launch {
            try {
                _state.value = FilmDetailsState.Loading
                val item = repository.getFilmInfo(id).toUi(isFavourite)
                _state.value = FilmDetailsState.Result(item)
            } catch (e: Throwable) {
                _state.value = FilmDetailsState.Error
            }
        }
    }

    class Factory(private val repo: IFilmsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilmDetailsViewModel(repo) as T
        }
    }
}
