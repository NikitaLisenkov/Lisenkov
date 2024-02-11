package com.example.kinopoiskapp.ui.films

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskapp.R
import com.example.kinopoiskapp.databinding.FragmentFilmsBinding
import com.example.kinopoiskapp.di.ServiceLocator
import com.example.kinopoiskapp.ui.MainActivity
import com.example.kinopoiskapp.ui.films.adapter.FilmsAdapter
import com.example.kinopoiskapp.utils.isLandscape
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class FilmsFragment : Fragment(R.layout.fragment_films) {

    private val binding: FragmentFilmsBinding by viewBinding()

    private val viewModel: FilmsViewModel by viewModels {
        FilmsViewModel.Factory(ServiceLocator.repository)
    }

    private val adapter: FilmsAdapter = FilmsAdapter(
        onClick = ::showFilmDetails,
        onLongClick = { viewModel.onLongClick(it) }
    )

    private var lastOpenedFilmId: Int = UNKNOWN_FILM_ID

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lastOpenedFilmId = savedInstanceState?.getInt(ID, UNKNOWN_FILM_ID) ?: UNKNOWN_FILM_ID

        if (isLandscape()) {
            showFilmDetails(
                viewModel.getFilm(lastOpenedFilmId)
            )
        }

        with(binding) {
            rvFilms.adapter = adapter
            initButtons()
            initSearchField()
        }

        viewModel.state
            .onEach(::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ID, lastOpenedFilmId)
    }

    private fun render(state: FilmsState) {
        with(binding) {
            when (state) {
                FilmsState.Error -> {
                    content.isVisible = false
                    errorScreen.root.isVisible = true
                    progressBar.isVisible = false
                }

                FilmsState.Loading -> {
                    content.isVisible = false
                    errorScreen.root.isVisible = false
                    progressBar.isVisible = true
                }

                is FilmsState.Content -> {
                    adapter.items = state.items
                    content.isVisible = true
                    errorScreen.root.isVisible = false
                    progressBar.isVisible = false
                    btnNotFound.isVisible = adapter.items.isEmpty()
                }
            }
        }
    }

    private fun initButtons() {
        with(binding) {
            errorScreen.btnRepeat.setOnClickListener {
                viewModel.loadFilms()
            }

            with(buttons) {
                btnFavourite.setOnClickListener {
                    viewModel.onFavoriteClick()
                }
                btnPopular.setOnClickListener {
                    viewModel.onPopularClick()
                }
            }
        }
    }

    private fun initSearchField() {
        with(binding) {
            with(searchToolbar) {
                etSearch.addTextChangedListener { query ->
                    viewModel.search(query?.toString().orEmpty())

                    if (query.toString().isNotBlank()) {
                        ivClose.isVisible = true
                        ivSearch.isVisible = false
                        ivClose.setOnClickListener { etSearch.text.clear() }
                    } else {
                        ivClose.isVisible = false
                        ivSearch.isVisible = true
                    }
                }
            }
        }
    }

    private fun showFilmDetails(film: FilmPreviewUi) {
        lastOpenedFilmId = film.id
        requireActivity().supportFragmentManager.setFragmentResult(
            MainActivity.RESULT_KEY,
            bundleOf(ID to film.id)
        )
    }

    companion object {
        const val UNKNOWN_FILM_ID = -1
        const val TAG = "FilmsFragment"
        const val ID = "id"
        const val IS_FAVOURITE = "is_favourite"
    }

}