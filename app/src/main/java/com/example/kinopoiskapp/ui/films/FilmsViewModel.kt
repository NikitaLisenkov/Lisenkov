package com.example.kinopoiskapp.ui.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskapp.data.repository.Mapper.toEntity
import com.example.kinopoiskapp.data.repository.Mapper.toUi
import com.example.kinopoiskapp.domain.IFilmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsViewModel(private val repository: IFilmsRepository) : ViewModel() {

    private val _state: MutableStateFlow<FilmsState> = MutableStateFlow(FilmsState.Loading)
    val state: Flow<FilmsState> = _state

    private val _search: MutableStateFlow<String> = MutableStateFlow("")

    private val cachedItems: MutableList<FilmPreviewUi> = mutableListOf()

    init {
        _search
            .debounce(400)
            .distinctUntilChanged()
            .flowOn(Dispatchers.Default)
            .onEach { query -> handleSearchQuery(query) }
            .launchIn(viewModelScope)
    }

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            _state.value = FilmsState.Loading
            try {
                loadRemoteFilmsAsync().await()
                loadLocaleFilmsAsync().await()
            } catch (e: Throwable) {
                _state.value = FilmsState.Error
                loadLocaleFilmsAsync().await()
            }
        }
    }

    fun onFavoriteClick() {
        val contentState = (_state.value as? FilmsState.Content) ?: return
        _state.value = contentState.copy(
            items = contentState.items.filter { it.favourite }
        )
    }

    fun onPopularClick() {
        val contentState = (_state.value as? FilmsState.Content) ?: return
        if (cachedItems.isNotEmpty()) {
            _state.value = contentState.copy(items = cachedItems)
        }
    }

    fun onLongClick(film: FilmPreviewUi) {
        val contentState = (_state.value as? FilmsState.Content) ?: return

        val updatedItems = contentState.items.map { item ->
            val haveSameIds = item.id == film.id
            val isFavourite = item.favourite

            when {
                haveSameIds && !isFavourite -> {
                    item.copy(favourite = true).also { addToDb(it) }
                }

                haveSameIds -> {
                    item.copy(favourite = false).also { deleteFromDb(it) }
                }

                else -> {
                    item
                }
            }
        }

        _state.value = contentState.copy(items = updatedItems)

        cachedItems.apply {
            this.clear()
            this.addAll(updatedItems)
        }
    }

    fun getFilm(id: Int): FilmPreviewUi {
        return cachedItems.find { it.id == id } ?: cachedItems[0]
    }

    fun search(userInput: String) {
        _search.value = userInput
    }

    private fun addToDb(film: FilmPreviewUi) {
        viewModelScope.launch {
            repository.addToDb(film.toEntity())
        }
    }

    private fun deleteFromDb(film: FilmPreviewUi) {
        viewModelScope.launch {
            repository.deleteFromDb(film.toEntity())
        }
    }

    private fun handleSearchQuery(query: String) {
        if (query.isNotBlank()) {
            val searchResult = cachedItems.filter { item ->
                val nameRuContainsQuery = item.nameRu.contains(query, true)
                val nameEnContainsQuery = item.nameEn.contains(query, true)
                val genresContainsQuery = item.genres.contains(query, true)
                nameRuContainsQuery || nameEnContainsQuery || genresContainsQuery
            }
            _state.value = FilmsState.Content(searchResult)
        } else if (cachedItems.isNotEmpty()) {
            _state.value = FilmsState.Content(cachedItems)
        }
    }

    private fun loadLocaleFilmsAsync() =
        viewModelScope.async {
            val local = repository.getAllFilmsFromDb().map { it.toUi() }

            when {
                cachedItems.isNotEmpty() -> withContext(Dispatchers.Default) {
                    cachedItems.map { cache ->
                        if (local.any { loc -> loc.id == cache.id }) {
                            cache.copy(favourite = true)
                        } else {
                            cache
                        }
                    }.also {
                        cachedItems.clear()
                        cachedItems.addAll(it)
                    }
                    _state.value = FilmsState.Content(cachedItems)
                }

                local.isNotEmpty() -> {
                    _state.value = FilmsState.Content(local)
                    cachedItems.addAll(local)
                }

                else -> {
                    _state.value = FilmsState.Error
                }
            }
        }

    private fun loadRemoteFilmsAsync() = viewModelScope.async {
        val remote = repository.getTopFilms().map { it.toUi() }
        cachedItems.addAll(remote)
        _state.value = FilmsState.Content(remote)
    }

    class Factory(private val repo: IFilmsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilmsViewModel(repo) as T
        }
    }
}