package com.moviemax

import com.moviemax.common.BaseUITest
import com.moviemax.di.AppModule
import com.moviemax.di.TestCommonTestModule
import com.moviemax.di.TestMovieDetailsModule
import com.moviemax.di.TestMoviesListModule
import com.moviemax.fake.getFakeMoviesTest
import com.moviemax.model.movie.data.toMovie
import com.moviemax.util.asserMovieDetailsDownloadCheck
import com.moviemax.util.asserMovieDetailsPlayCheck
import com.moviemax.util.asserMovieDetailsScrollCheck
import com.moviemax.util.assertBackPress
import com.moviemax.util.assertMovieDetailsCheck
import com.moviemax.util.assertMovieListItemClickCheck
import com.moviemax.util.assertMovieListScrollBottom
import com.moviemax.util.assertMovieListScrollTop

import org.junit.Test

class MainActivityTest : BaseUITest(
    listOf(AppModule, TestCommonTestModule, TestMoviesListModule, TestMovieDetailsModule)
) {

    private val fakeMovies = getFakeMoviesTest().map {
        it.toMovie()
    }


    @Test
    fun MovieMaxApp_loaded_with_list_of_movies_and_scroll_check() {
        with(composeTestRule) {
            assertMovieListScrollBottom(fakeMovies.last())
        }
    }

    @Test
    fun MovieMaxApp_loaded_with_list_of_movies_and_click_on_last_item() {
        with(composeTestRule) {
            assertMovieListItemClickCheck(fakeMovies)
            assertMovieDetailsCheck(fakeMovies.last())
            asserMovieDetailsScrollCheck()
        }
    }

    @Test
    fun MovieMaxApp_loaded_movies_and_click_on_item_snd_verify_data_and_come_back() {
        with(composeTestRule) {
            assertMovieListItemClickCheck(fakeMovies)
            assertMovieDetailsCheck(fakeMovies.last())
            asserMovieDetailsScrollCheck()
            assertBackPress()
            assertMovieListScrollTop(fakeMovies.first())
        }
    }

    @Test
    fun MovieMaxApp_loaded_with_list_of_movies_and_play_selected_content() {
        with(composeTestRule) {
            assertMovieListItemClickCheck(fakeMovies)
            assertMovieDetailsCheck(fakeMovies.last())
            asserMovieDetailsPlayCheck(fakeMovies.last())
        }
    }

    @Test
    fun MovieMaxApp_loaded_with_list_of_movies_and_download_content() {
        with(composeTestRule) {
            assertMovieListItemClickCheck(fakeMovies)
            assertMovieDetailsCheck(fakeMovies.last())
            asserMovieDetailsDownloadCheck()
        }
    }
}