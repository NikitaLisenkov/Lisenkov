package com.example.kinopoiskapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskapp.ui.films.FilmPreviewUi

class ItemDiffUtil(
    private val oldList: List<FilmPreviewUi>,
    private val newList: List<FilmPreviewUi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}
