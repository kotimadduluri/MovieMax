package com.moviemax.view.movie.details

import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.moviemax.common.BaseUITest
import com.moviemax.di.DI_AppModule
import com.moviemax.di.TestCommonTestModule
import com.moviemax.di.TestMovieDetailsModule
import com.moviemax.di.mockNoNetwork
import com.moviemax.fake.movieId
import com.moviemax.model.movie.getFakeMovie
import com.common.ui.theme.MovieMaxTheme
import com.moviemax.util.asserMovieDetailsCheck
import com.moviemax.util.asserMovieDetailsDownloadCheck
import com.moviemax.util.asserMovieDetailsPlayCheck
import com.moviemax.util.asserMovieDetailsScrollCheck
import com.moviemax.util.assertMovieDetailsCheck
import com.moviemax.util.assertNoNetworkCheck
import com.moviemax.viewmodel.movie.MovieDetailsScreenViewModel
import org.junit.Before
import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.test.mock.declare

class MovieDetailsScreenKtTest : BaseUITest(
    listOf(DI_AppModule, TestCommonTestModule, TestMovieDetailsModule)
) {

    private val fakeMovie = getFakeMovie(movieId)

    @Before
    fun setup() {
        // Start the app
        composeTestRule.activity.setContent {
            MovieMaxTheme {
                val viewModel: MovieDetailsScreenViewModel = koinViewModel()
                val state = viewModel.uiState().collectAsStateWithLifecycle()
                MovieDetailsScreen(
                    movieId = movieId,
                    uiState = state,
                    navHostController = rememberNavController()
                ) { intent ->
                    viewModel.onAction(intent)
                }
            }
        }
        //composeTestRule.onRoot(useUnmergedTree = true).printToLog("Movie Details :: ")
    }

    @Test
    fun whenDetails_loaded_data_successfully() {
        composeTestRule.assertMovieDetailsCheck(fakeMovie!!)
    }

    @Test
    fun whenDetails_loaded_perform_gallery_scroll() {
        composeTestRule.asserMovieDetailsCheck(fakeMovie!!)
        composeTestRule.asserMovieDetailsScrollCheck()
    }

    @Test
    fun whenDetails_loaded_perform_play_click() {
        composeTestRule.asserMovieDetailsPlayCheck(fakeMovie!!)
    }

    @Test
    fun whenDetails_loaded_perform_download_click() {
        composeTestRule.asserMovieDetailsDownloadCheck()
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