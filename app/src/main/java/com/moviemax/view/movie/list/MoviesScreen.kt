package com.moviemax.view.movie.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.ui.components.common.AppContainer
import com.moviemax.ui.components.list.VerticalList
import com.moviemax.ui.components.state.ActionState
import com.moviemax.ui.components.state.ActionStateView
import com.moviemax.ui.components.state.ActionStateViewCard
import com.moviemax.view.movie.UiState

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
                    VerticalList(
                        data = state.data
                    ) { movie ->
                        MovieCard(movie = movie) {
                            event(MoviesScreenIntent.VIEW_DETAILS(it))
                        }
                    }
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(),
                        isActionRequired = true
                    ) {
                        event(MoviesScreenIntent.REFRESH)
                    }
                }

                else -> {
                    //todo for future development purpose
                }
            }
        }
    }
}

sealed class MoviesScreenIntent {

    object REFRESH : MoviesScreenIntent()

    data class VIEW_DETAILS(val movie: Movie) : MoviesScreenIntent()

}