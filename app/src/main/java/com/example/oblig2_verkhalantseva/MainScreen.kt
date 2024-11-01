package com.example.oblig2_verkhalantseva

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oblig2_verkhalantseva.data.getEnduranceExercises
import com.example.oblig2_verkhalantseva.data.getStrengthExercises
import com.example.oblig2_verkhalantseva.data.model.Exercise
import com.example.oblig2_verkhalantseva.ui.theme.Oblig2_VerkhalantsevaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.onBackground),
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            style = MaterialTheme.typography.displayLarge,
                            text = stringResource(id = R.string.top_bar),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.weight(2f))
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.trening),
                            contentDescription = "",
                            modifier = Modifier
                                .size(width = 50.dp, height = 50.dp)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {

            MainContent()

        }
    }
}

@Composable
fun MainContent(
) {
    val exercises = remember { mutableStateOf(DataHelper.currentStrengthExercises) }
    val amountOfFinishedExercises = remember { mutableStateOf(0) }
    val amountOfFinishedReps = remember { mutableStateOf(0) }
    val currentExercisesSelected = remember { mutableStateOf(ExerciseType.STRENGTH) }
    val totalExercises = getStrengthExercises().size + getEnduranceExercises().size
    val totalReps = getStrengthExercises().sumOf { item ->
        item.reps.size
    } + getEnduranceExercises().sumOf { item ->
        item.reps.size
    }
    val (finishedExercises, finishedReps) = countExerciseDetails(exercises.value)
    val context = LocalContext.current

    var checked by remember { mutableStateOf(true) }

    fun updateExerciseSelection(
        currentTypeSelected: MutableState<ExerciseType>,
        newType: ExerciseType,
        newExercises: List<Exercise>
    ) {
        if (currentTypeSelected.value == newType) return
        saveCurrentExercises(exercises.value, currentTypeSelected.value)
        currentTypeSelected.value = newType
        exercises.value = newExercises.toMutableStateList()
        val (allFinishedExercises, allFinishedReps) = countExerciseDetails(exercises.value)
        amountOfFinishedExercises.value = allFinishedExercises
        amountOfFinishedReps.value = allFinishedReps
    }


    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 4.dp, bottom = 4.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    updateExerciseSelection(
                        currentExercisesSelected,
                        ExerciseType.STRENGTH,
                        DataHelper.currentStrengthExercises
                    )
                },
                enabled = currentExercisesSelected.value != ExerciseType.STRENGTH,
                modifier = Modifier
                    .weight(1f)
                    .testTag("StrengthButton"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.outline,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = shapes.small
            ) {
                Text(
                    text = stringResource(id = R.string.strength_button),
                    style = MaterialTheme.typography.displaySmall
                )
            }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = {
                    updateExerciseSelection(
                        currentExercisesSelected,
                        ExerciseType.ENDURANCE,
                        DataHelper.currentEnduranceExercises
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .testTag("EnduranceButton"),
                enabled = currentExercisesSelected.value != ExerciseType.ENDURANCE,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.outline,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = shapes.small
            ) {
                Text(
                    text = stringResource(id = R.string.endurance_button),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }

        Text(
            text = stringResource(
                id = R.string.number_of_exercises,
                totalExercises,
                totalReps
            ),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .padding(start = 10.dp, top = 8.dp)
                .testTag("total_number_of_exercises_and_sets")
        )
        Text(
            text = stringResource(id = R.string.complete_exercises, finishedExercises),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .padding(start = 10.dp, top = 6.dp)
                .testTag("number_of_completed_exercises")
        )
        Text(
            text = stringResource(id = R.string.complete_sets, finishedReps),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .padding(start = 10.dp, top = 4.dp, bottom = 8.dp)
                .testTag("number_of_completed_sets_for_all_exercises")
        )


        LazyColumn(
            modifier = Modifier.testTag("ExercisesLazyColumn")
        ) {
            itemsIndexed(exercises.value) { exerciseIndex, exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .border(
                            border = BorderStroke(
                                width = 2.dp,
                                MaterialTheme.colorScheme.onBackground
                            )
                        )
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                delayMillis = 0
                            )
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = shapes.large

                ) {
                    if (exercise.isExpanded) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .weight(1f, false)
                                    .padding(start = 10.dp, top = 10.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = exercise.imageId),
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.FillWidth
                                )
                                Text(
                                    style = MaterialTheme.typography.labelSmall,
                                    text = stringResource(id = exercise.videoTitle),
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier.clickable {
                                        val intent =
                                            Intent(Intent.ACTION_VIEW, Uri.parse(exercise.link))
                                        context.startActivity(intent)
                                    }
                                )

                            }
                            Column(
                                modifier = Modifier.weight(4f, false)
                            ) {
                                Text(
                                    style = MaterialTheme.typography.displayMedium,
                                    text = stringResource(id = exercise.title),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 10.dp,
                                        end = 16.dp,
                                        bottom = 8.dp
                                    )
                                )
                                Text(
                                    style = MaterialTheme.typography.bodySmall,
                                    text = stringResource(id = exercise.description),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 6.dp,
                                        end = 16.dp,
                                        bottom = 8.dp
                                    )
                                )
                                LazyColumn(
                                    Modifier.height((exercise.reps.size * 50).dp)
                                ) {
                                    itemsIndexed(exercise.reps) { index, rep ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            //Log.e("WatchingSomeStuff", "Rep Row")
                                            Switch(
                                                modifier = Modifier
                                                    .testTag("SetSwitch$exerciseIndex$index"),
                                                checked = rep.wasFinished,
                                                onCheckedChange = {
                                                    exercises.value[exerciseIndex].reps[index].wasFinished =
                                                        it
                                                    amountOfFinishedReps.value +=
                                                        if (it) 1
                                                        else -1
                                                    if (it) {
                                                        if (!exercise.reps.any { eachRep -> !eachRep.wasFinished })
                                                            amountOfFinishedExercises.value += 1
                                                    } else {
                                                        if (exercise.reps.count { eachRep -> !eachRep.wasFinished } == 1)
                                                            amountOfFinishedExercises.value -= 1
                                                    }
                                                    val exerciseCopy =
                                                        exercises.value[exerciseIndex]
                                                    exercises.value.removeAt(exerciseIndex)
                                                    exercises.value.add(exerciseIndex, exerciseCopy)
                                                },
                                                thumbContent = if (checked) {
                                                    {
                                                        Icon(
                                                            imageVector = Icons.Filled.Check,
                                                            contentDescription = null,
                                                            modifier = Modifier.size(SwitchDefaults.IconSize),
                                                        )
                                                    }
                                                } else {
                                                    null
                                                }
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .padding(start = 15.dp),
                                                text = stringResource(
                                                    id = R.string.rep_placeholder,
                                                    rep.number
                                                ),
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                        }
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier.weight(1f, false)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.triangle_small_up
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier.clickable {
                                        exercises.value[exerciseIndex].isExpanded = false
                                        val exerciseCopy =
                                            exercises.value[exerciseIndex]
                                        exercises.value.removeAt(exerciseIndex)
                                        exercises.value.add(exerciseIndex, exerciseCopy)
                                    }
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(start = 15.dp),
                                style = MaterialTheme.typography.displayMedium,
                                text = stringResource(id = exercise.title),
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Image(
                                painter = painterResource(
                                    id = R.drawable.triangle_small_down
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("Exercise$exerciseIndex")
                                    .clickable {
                                        exercises.value[exerciseIndex].isExpanded = true
                                        val exerciseCopy =
                                            exercises.value[exerciseIndex]
                                        exercises.value.removeAt(exerciseIndex)
                                        exercises.value.add(exerciseIndex, exerciseCopy)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun countExerciseDetails(exercises: MutableList<Exercise>): Pair<Int, Int> {
    var amountOfFinishedExercises = 0
    var amountOfFinishedReps = 0

    exercises.forEach { exercise ->
        if (exercise.reps.all { it.wasFinished }) {
            amountOfFinishedExercises++
        }
        amountOfFinishedReps += exercise.reps.count { it.wasFinished }
    }

    return Pair(amountOfFinishedExercises, amountOfFinishedReps)
}

fun saveCurrentExercises(exercises: MutableList<Exercise>, exerciseType: ExerciseType) {
    when (exerciseType) {
        ExerciseType.STRENGTH -> DataHelper.currentStrengthExercises = exercises
        ExerciseType.ENDURANCE -> DataHelper.currentEnduranceExercises = exercises
    }
}

enum class ExerciseType {
    STRENGTH, ENDURANCE
}

class DataHelper {

    companion object {
        var currentStrengthExercises = getStrengthExercises()
        var currentEnduranceExercises = getEnduranceExercises()
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    Oblig2_VerkhalantsevaTheme {
        MainScreen()
    }
}

@Preview
@Composable
fun MainDarkScreenPreview() {
    Oblig2_VerkhalantsevaTheme(
        darkTheme = true
    ) {
        MainScreen()
    }
}




