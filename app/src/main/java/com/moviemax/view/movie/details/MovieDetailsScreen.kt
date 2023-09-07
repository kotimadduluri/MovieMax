package com.moviemax.view.movie.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.ui.components.common.AppContainer
import com.moviemax.ui.components.common.DefaultNavigationIcon
import com.moviemax.ui.components.slider.ImageSlider
import com.moviemax.ui.components.state.ActionState
import com.moviemax.ui.components.state.ActionStateView
import com.moviemax.ui.components.state.ActionStateViewCard
import com.moviemax.view.movie.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    uiState: State<UiState>,
    navHostController: NavHostController,
    event: (intent: MovieDetailsScreenIntent) -> Unit
) {

    val scrollState: ScrollState = rememberScrollState(0)

    LaunchedEffect(Unit) {
        event(MovieDetailsScreenIntent.GET_DETAILS(movieId))
    }

    AppContainer(
        navigation = {
            DefaultNavigationIcon {
                navHostController.popBackStack()
            }
        }
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
                    val response = (uiState.value as UiState.Success<Movie>).data
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        with(response) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .verticalScroll(state = scrollState)
                            ) {
                                pictures?.let {
                                    ImageSlider(
                                        it
                                    )
                                }
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    description?.let {
                                        Text(text = it)
                                    }
                                }

                            }
                        }
                    }
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(),
                        isActionRequired = true
                    ) {
                        event(MovieDetailsScreenIntent.REFRESH(movieId))
                    }
                }

                else -> {

                }
            }
        }
    }
}

sealed class MovieDetailsScreenIntent {
    data class GET_DETAILS(val movieId: Int) : MovieDetailsScreenIntent()
    data class REFRESH(val movieId: Int) : MovieDetailsScreenIntent()
}