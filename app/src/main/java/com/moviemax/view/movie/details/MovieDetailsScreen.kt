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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.common.R
import com.common.util.UiText
import com.moviemax.model.movie.data.domain.model.Movie
import com.common.ui.components.button.ButtonWithProgressBar
import com.common.ui.components.AppContainer
import com.common.ui.components.actionbar.DefaultNavigationIcon
import com.common.ui.components.icon.IconWithDrawable
import com.common.ui.components.slider.ImageSlider
import com.common.ui.components.state.ActionState
import com.common.ui.components.state.ActionStateView
import com.common.ui.components.state.ActionStateViewCard
import com.common.ui.components.text.TextView
import com.common.ui.components.text.TitleTextView
import com.common.ui.theme.GetColors
import com.common.ui.theme.spacing
import com.common.util.UiImage
import com.moviemax.view.UiState
import com.moviemax.viewmodel.movie.MovieDetailsScreenIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    uiState: State<UiState>,
    navHostController: NavHostController,
    event: (intent: MovieDetailsScreenIntent) -> Unit
) {

    val isFavourite = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        event(MovieDetailsScreenIntent.GetDetails(movieId))
    }

    AppContainer(
        navigation = {
            DefaultNavigationIcon {
                navHostController.popBackStack()
            }
        },
        toolbarActions = {
            IconWithDrawable(
                icon = UiImage.DrawableResource(
                    if (isFavourite.value) {
                        R.drawable.ic_favorite_active
                    } else R.drawable.ic_favorite_inactive
                )
            ) {
                isFavourite.value = !isFavourite.value
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
                        action = ActionState.LOADING()
                    )
                }

                is UiState.Success<*> -> {
                    val response = (uiState.value as UiState.Success<Movie>).data
                    MovieDetailsSection(response)
                }

                is UiState.Error -> {
                    ActionStateView(
                        action = ActionState.ERROR(
                            message = (uiState.value as UiState.Error).message
                                ?: UiText.StringResource(R.string.error_something_went_wrong)
                        ),
                        isActionRequired = true
                    ) {
                        event(MovieDetailsScreenIntent.Refresh(movieId))
                    }
                }

                else -> {}
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
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(state = scrollState)
            ) {
                //image gallery
                pictures?.let {
                    ImageSlider(it)
                }

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))

                TitleTextView(
                    text = UiText.PlainString(name),
                    textStyle = MaterialTheme.typography.titleLarge,
                    fontColor = MaterialTheme.colorScheme.onBackground
                )

                Row {
                    TextView(
                        text = UiText.PlainString("Rating : $rating ($ratingCount) | "),
                        textStyle = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp
                    )

                    TextView(
                        text = UiText.PlainString(movie.status),
                        textStyle = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Light,
                        fontColor = GetColors.movieCardStatusColor(isSystemInDarkTheme()),
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))

                ButtonWithProgressBar(
                    text = UiText.StringResource(com.moviemax.R.string.play)
                )

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.extraSmall))

                ButtonWithProgressBar(
                    text = UiText.StringResource(com.moviemax.R.string.download)
                )

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))

                TextView(
                    text = UiText.PlainString(description ?: "No description available"),
                    fontWeight = FontWeight.Normal
                )

            }
        }
    }
}