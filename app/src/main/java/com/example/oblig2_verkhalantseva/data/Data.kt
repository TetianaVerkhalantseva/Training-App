package com.example.oblig2_verkhalantseva.data

import androidx.compose.runtime.mutableStateListOf
import com.example.oblig2_verkhalantseva.R
import com.example.oblig2_verkhalantseva.data.model.Exercise
import com.example.oblig2_verkhalantseva.data.model.Rep

fun getStrengthExercises(): MutableList<Exercise> =
    mutableStateListOf(
        Exercise(
            imageId = R.drawable.benkpress,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.benkpress,
            description = R.string.benkpress_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        ),
        Exercise(
            imageId = R.drawable.skulderpress,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.skulderpress,
            description = R.string.skulderpress_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        ),
        Exercise(
            imageId = R.drawable.nedtrekk,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.nedtrekk,
            description = R.string.nedtrekk_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        ),
        Exercise(
            imageId = R.drawable.biceps,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.biceps,
            description = R.string.biceps_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        )
    )

fun getEnduranceExercises(): MutableList<Exercise> =
    mutableStateListOf(
        Exercise(
            imageId = R.drawable.jumping,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.jumping_jacks,
            description = R.string.jumping_jacks_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        ),
        Exercise(
            imageId = R.drawable.burpees,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.burpees,
            description = R.string.burpees_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        ),
        Exercise(
            imageId = R.drawable.kneloft,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.kneløft,
            description = R.string.kneløft_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        ),
        Exercise(
            imageId = R.drawable.intervalltrening,
            link = "https://uit.no/enhet/ivt",
            videoTitle = R.string.video,
            title = R.string.intervalltrening,
            description = R.string.intervalltrening_discription,
            reps = listOf(
                Rep(
                    number = 1
                ),
                Rep(
                    number = 2
                ),
                Rep(
                    number = 3
                ),
                Rep(
                    number = 4
                )
            )
        )
    )
