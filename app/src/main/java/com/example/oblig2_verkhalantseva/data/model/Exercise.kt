package com.example.oblig2_verkhalantseva.data.model

import androidx.annotation.DrawableRes

class Exercise(
    @DrawableRes val imageId: Int,
    var link: String,
    val videoTitle: Int,
    val title: Int,
    val description: Int,
    val reps: List<Rep>,
    var isExpanded: Boolean = false
) {}