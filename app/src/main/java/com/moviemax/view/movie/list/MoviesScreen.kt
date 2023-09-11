package com.moviemax.view.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.common.util.UiText
import com.moviemax.model.movie.data.domain.model.Movie
import com.common.ui.components.AppContainer
import com.common.ui.components.list.VerticalList
import com.common.ui.components.state.ActionState
import com.common.ui.components.state.ActionStateView
import com.common.ui.components.state.ActionStateViewCard
import com.moviemax.R
import com.moviemax.view.movie.UiState
import com.moviemax.view.movie.UiState.None.asError
import com.moviemax.view.movie.UiState.None.asSuccess
import com.moviemax.viewmodel.MoviesScreenIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    uiState: State<UiState>,
    event: (intent: MoviesScreenIntent) -> Unit
) {

    AppContainer(
        title = UiText.StringResource(R.string.movies),
        navigation = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (uiState.value) {
                is UiState.Loading -> {
                    ActionStateViewCard(
                        action = ActionState.LOADING()
                    )
                }

                is UiState.Success<*> -> {
                    val state = uiState.value.asSuccess<List<Movie>>()
                    MoviesList(state.data) { intent ->
                        event(intent)
                    }
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(
                            message = uiState.value.asError().message
                        ),
                        isActionRequired = true
                    ) {
                        event(MoviesScreenIntent.Refresh)
                    }
                }

                else -> {
                    //todo for future development purpose
                }
            }
        }
    }
}


@Composable
fun MoviesList(
    data: List<Movie>,
    modifier: Modifier = Modifier,
    event: (intent: MoviesScreenIntent) -> Unit
) {

    val dataElements = remember {
        data
    }

    VerticalList(
        data = dataElements,
        modifier = modifier.testTag("MoviesList")
    ) { movie ->
        MovieCard(movie = movie) {
            event(MoviesScreenIntent.ViewDetails(it))
        }
    }
}