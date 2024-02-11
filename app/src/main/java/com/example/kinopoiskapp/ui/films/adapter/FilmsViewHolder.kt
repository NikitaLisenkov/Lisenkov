package com.example.kinopoiskapp.ui.films.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoiskapp.R
import com.example.kinopoiskapp.databinding.ItemFilmBinding
import com.example.kinopoiskapp.ui.films.FilmPreviewUi

class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemFilmBinding = ItemFilmBinding.bind(itemView)

    fun bind(item: FilmPreviewUi) {
        with(binding) {
            Glide.with(itemView).load(item.posterUrlPreview).into(image)
            name.text = item.nameRu
            genre.text = itemView.context.getString(R.string.genre_template, item.genres, item.year)
            favourite.isVisible = item.favourite
        }
    }
}
