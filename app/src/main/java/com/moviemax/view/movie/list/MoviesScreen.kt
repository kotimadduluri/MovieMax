package com.moviemax.view.movie.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.ui.components.list.VerticalList
import com.moviemax.ui.components.state.ActionState
import com.moviemax.ui.components.state.ActionStateView
import com.moviemax.ui.components.state.ActionStateViewCard
import com.moviemax.view.movie.UiState

@Composable
fun MoviesScreen(
    uiState: State<UiState>,
    event: (intent: MoviesScreenIntent) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState.value) {
            is UiState.Loading -> {
                ActionStateViewCard(
                    action = ActionState.LOADING()
                ) {

                }
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

sealed class MoviesScreenIntent {

    object REFRESH : MoviesScreenIntent()
    data class VIEW_DETAILS(val movie: Movie) : MoviesScreenIntent()
}