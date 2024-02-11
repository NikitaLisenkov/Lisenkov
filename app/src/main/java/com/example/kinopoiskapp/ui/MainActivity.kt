package com.example.kinopoiskapp.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kinopoiskapp.R
import com.example.kinopoiskapp.ui.film_details.FilmDetailsFragment
import com.example.kinopoiskapp.ui.films.FilmsFragment
import com.example.kinopoiskapp.utils.setUpEdgeToEdge

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(FilmsFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_list, FilmsFragment(), FilmsFragment.TAG)
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(RESULT_KEY, this) { _, result ->
            val filmId = result.getInt(FilmsFragment.ID, 0)
            val detailsFragment = FilmDetailsFragment.newInstance(filmId)
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_list, detailsFragment, FilmDetailsFragment.TAG)
                    .addToBackStack(null)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_details, detailsFragment, FilmDetailsFragment.TAG)
                    .commit()
            }
        }
    }

    companion object {
        const val RESULT_KEY = "FRAGMENT_RESULT_KEY"
    }
}