package com.example.oblig2_verkhalantseva

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.oblig2_verkhalantseva.data.getEnduranceExercises
import com.example.oblig2_verkhalantseva.data.getStrengthExercises
import com.example.oblig2_verkhalantseva.ui.theme.Oblig2_VerkhalantsevaTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenUITest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.setContent {
            Oblig2_VerkhalantsevaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Test
    fun checkButtonIsEnabled() {
        composeTestRule.onNodeWithTag("StrengthButton")
            .assertIsNotEnabled()

        composeTestRule.onNodeWithTag("EnduranceButton")
            .assertIsEnabled()
    }

    @Test
    fun checkIsTotalNumberOfExercisesAndSets() {
        composeTestRule.onNodeWithTag("total_number_of_exercises_and_sets")
            .assertTextEquals(
                context.getString(
                    R.string.number_of_exercises, 8,
                    32
                )
            )
    }


    @Test
    fun checkIsNumberOfCompletedExercisesAndNumberOfCompletedSets() {
        composeTestRule.onNodeWithTag("number_of_completed_exercises")
            .assertTextEquals(context.getString(R.string.complete_exercises, 0))

        var exercises = getStrengthExercises()
        composeTestRule.onNodeWithTag("Exercise0").performClick()
        exercises[0].reps.forEachIndexed { index, rep ->
            composeTestRule.onNodeWithTag("SetSwitch0$index").performClick()
        }

        composeTestRule.onNodeWithTag("number_of_completed_sets_for_all_exercises")
            .assertTextEquals(context.getString(R.string.complete_sets, exercises[0].reps.size))
        composeTestRule.onNodeWithTag("number_of_completed_exercises")
            .assertTextEquals(context.getString(R.string.complete_exercises, 1))

        if (exercises[0].reps.isNotEmpty()) {
            composeTestRule.onNodeWithTag("SetSwitch00").performClick()
        }

        composeTestRule.onNodeWithTag("number_of_completed_sets_for_all_exercises")
            .assertTextEquals(context.getString(R.string.complete_sets, exercises[0].reps.size - 1))
        composeTestRule.onNodeWithTag("number_of_completed_exercises")
            .assertTextEquals(context.getString(R.string.complete_exercises, 0))

        if (exercises.size > 1) {
            composeTestRule.onNodeWithTag("Exercise1").performClick()
            composeTestRule
                .onNodeWithTag("ExercisesLazyColumn")
                .performScrollToIndex(1)
            exercises[1].reps.forEachIndexed { index, rep ->
                composeTestRule.onNodeWithTag("SetSwitch1$index")
                    .performClick()
            }
            composeTestRule.onNodeWithTag("number_of_completed_sets_for_all_exercises")
                .assertTextEquals(
                    context.getString(
                        R.string.complete_sets,
                        exercises[0].reps.size - 1 + exercises[1].reps.size
                    )
                )
            composeTestRule.onNodeWithTag("number_of_completed_exercises")
                .assertTextEquals(context.getString(R.string.complete_exercises, 1))

            composeTestRule.onNodeWithTag("EnduranceButton")
                .performClick()

            composeTestRule.onNodeWithTag("number_of_completed_sets_for_all_exercises")
                .assertTextEquals(
                    context.getString(
                        R.string.complete_sets,
                        0
                    )
                )
            composeTestRule.onNodeWithTag("number_of_completed_exercises")
                .assertTextEquals(context.getString(R.string.complete_exercises, 0))

            exercises = getEnduranceExercises()
            composeTestRule.onNodeWithTag("Exercise0").performClick()
            exercises[0].reps.forEachIndexed { index, rep ->
                composeTestRule.onNodeWithTag("SetSwitch0$index").performClick()
            }

            composeTestRule.onNodeWithTag("number_of_completed_sets_for_all_exercises")
                .assertTextEquals(
                    context.getString(
                        R.string.complete_sets,
                        exercises[0].reps.size
                    )
                )
            composeTestRule.onNodeWithTag("number_of_completed_exercises")
                .assertTextEquals(context.getString(R.string.complete_exercises, 1))
        }


    }

}