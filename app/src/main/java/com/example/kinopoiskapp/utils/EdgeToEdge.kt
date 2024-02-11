package com.example.kinopoiskapp.utils

import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams

fun ComponentActivity.setUpEdgeToEdge() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val view = findViewById<View>(android.R.id.content)
    val resources = view.resources
    val transparent = ResourcesCompat.getColor(resources, android.R.color.transparent, theme)
    window.statusBarColor = transparent
    window.navigationBarColor = transparent
    val controller = WindowInsetsControllerCompat(window, view)
    controller.isAppearanceLightStatusBars = true
    controller.isAppearanceLightNavigationBars = true
    ViewCompat.setOnApplyWindowInsetsListener(view) { root, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = insets.bottom
        }
        WindowInsetsCompat.CONSUMED
    }
}