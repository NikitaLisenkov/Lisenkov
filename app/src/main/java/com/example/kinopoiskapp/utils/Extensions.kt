package com.example.kinopoiskapp.utils

import android.content.res.Configuration
import androidx.fragment.app.Fragment

fun Fragment.isLandscape(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE