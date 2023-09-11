package com.moviemax.view.movie.list

import androidx.activity.compose.setContent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moviemax.common.BaseUITest
import com.moviemax.di.AppModule
import com.moviemax.di.TestCommonTestModule
import com.moviemax.di.TestMoviesListModule
import com.moviemax.di.mockNoNetwork
import com.moviemax.di.mockWithNetwork
import com.moviemax.fake.getFakeMoviesTest
import com.moviemax.model.movie.data.toMovie
import com.common.ui.theme.MovieMaxTheme
import com.moviemax.util.assertMovieListItemClickCheck
import com.moviemax.util.assertMovieListScrollBottom
import com.moviemax.util.assertNoNetworkCheck
import com.moviemax.util.assertNoNetworkWithRetryButtonCheck
import com.moviemax.util.assertNoNetworkWithRetryButtonClickCheck
import com.moviemax.viewmodel.MoviesScreenViewModel
import org.junit.Before

import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.test.mock.declare

class MoviesScreenKtTest : BaseUITest(
    listOf(AppModule, TestCommonTestModule, TestMoviesListModule)
) {

    private val fakeMovies = getFakeMoviesTest().map {
        it.toMovie()
    }

    @Before
    fun setup() {
        // Start the app
        composeTestRule.activity.setContent {
            MovieMaxTheme {
                val viewModel: MoviesScreenViewModel = koinViewModel()
                val state = viewModel.uiState().collectAsStateWithLifecycle()
                MoviesScreen(
                    uiState = state
                ) { intent ->
                    viewModel.onAction(intent)
                }
            }
        }
    }

    @Test
    fun moviesScreen_movie_item_scroll_check() {
        composeTestRule.assertMovieListScrollBottom(fakeMovies.last())
    }

    @Test
    fun moviesScreen_movie_item_click_check_success() {
        composeTestRule.assertMovieListItemClickCheck(fakeMovies)
    }

    @Test
    fun MovieMaxApp_no_internet_on_movies_screen() {

        with(composeTestRule) {
            declare {
                mockNoNetwork()
            }
            assertNoNetworkCheck()
        }
    }

}