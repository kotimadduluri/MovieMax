package com.moviemax.view.movie.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.ui.components.button.ButtonWithProgressBar
import com.moviemax.ui.components.common.AppContainer
import com.moviemax.ui.components.common.DefaultNavigationIcon
import com.moviemax.ui.components.slider.ImageSlider
import com.moviemax.ui.components.state.ActionState
import com.moviemax.ui.components.state.ActionStateView
import com.moviemax.ui.components.state.ActionStateViewCard
import com.moviemax.ui.theme.GetColors
import com.moviemax.view.movie.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    uiState: State<UiState>,
    navHostController: NavHostController,
    event: (intent: MovieDetailsScreenIntent) -> Unit
) {
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
                    MovieDetailsSection(response)
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

@Composable
fun MovieDetailsSection(movie: Movie) {
    val scrollState: ScrollState = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        with(movie) {
            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxHeight()
                    .verticalScroll(state = scrollState)
            ) {
                //image gallery
                pictures?.let {
                    ImageSlider(it)
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )


                Row {
                    Text(
                        text = "Rating : $rating ($ratingCount) | ",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 8.sp
                    )

                    Text(
                        text = movie.status,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Light,
                        color = GetColors.movieCardStatusColor(isSystemInDarkTheme()),
                        fontSize = 8.sp
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                ButtonWithProgressBar(
                    text = "Play"
                ){
                    //future development
                }

                ButtonWithProgressBar(
                    text = "Download"
                ){
                    //future development
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(text = description ?: "No description available")

            }
        }
    }
}

sealed class MovieDetailsScreenIntent {
    data class GET_DETAILS(val movieId: Int) : MovieDetailsScreenIntent()
    data class REFRESH(val movieId: Int) : MovieDetailsScreenIntent()
}