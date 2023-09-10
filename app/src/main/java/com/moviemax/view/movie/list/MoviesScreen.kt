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
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.ui.components.common.AppContainer
import com.moviemax.ui.components.list.VerticalList
import com.moviemax.ui.components.state.ActionState
import com.moviemax.ui.components.state.ActionStateView
import com.moviemax.ui.components.state.ActionStateViewCard
import com.moviemax.view.movie.UiState
import com.moviemax.viewmodel.MoviesScreenIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    uiState: State<UiState>,
    event: (intent: MoviesScreenIntent) -> Unit
) {

    AppContainer(
        title = "Movies",
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
                        action = ActionState.LOADING(
                            message = "Looking for content"
                        )
                    )
                }

                is UiState.Success<*> -> {
                    val state = uiState.value as UiState.Success<List<Movie>>
                    MoviesList(state.data) { intent ->
                        event(intent)
                    }
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(
                            message = (uiState.value as UiState.Error).message.asString()
                                ?: "Something went wrong"
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