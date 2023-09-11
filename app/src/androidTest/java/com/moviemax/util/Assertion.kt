package com.moviemax.util

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.test.espresso.Espresso
import com.moviemax.fake.FAKE_NETWORK_ERROR
import com.moviemax.model.movie.data.domain.model.Movie

//error states
fun SemanticsNodeInteractionsProvider.assertNoNetworkCheck(){
    onNodeWithTag("ActionStateView").assertExists()
    onNodeWithText(FAKE_NETWORK_ERROR).assertExists()
}

fun SemanticsNodeInteractionsProvider.assertNoNetworkWithRetryButtonCheck(){
    onNodeWithTag("ActionStateView").assertExists()
    onNodeWithText(FAKE_NETWORK_ERROR).assertExists()
    onNodeWithText("Retry").assertExists()
}

fun SemanticsNodeInteractionsProvider.assertNoNetworkWithRetryButtonClickCheck(){
    onNodeWithTag("ActionStateView").assertExists()
    onNodeWithText(FAKE_NETWORK_ERROR).assertExists()
    onNodeWithText("Retry").assertExists()
    onNodeWithText("Retry").performClick()
}

//List screen

fun SemanticsNodeInteractionsProvider.assertMovieListScreenWithData(){
    onNodeWithTag("MoviesList").assertExists()  //list view available
}

fun SemanticsNodeInteractionsProvider.assertMovieListScrollBottom(movie:Movie){
    onNodeWithTag("MoviesList").assertExists()  //list view available

    onNodeWithTag("MoviesList").performTouchInput {
        swipeUp()
    }
    onNodeWithTag("${movie.id}").assertExists()
}

fun SemanticsNodeInteractionsProvider.assertMovieListScrollTop(movie:Movie){
    onNodeWithTag("MoviesList").assertExists()  //list view available

    onNodeWithTag("MoviesList").performTouchInput {
        swipeDown()
    }
    onNodeWithTag("${movie.id}").assertExists()
}

fun SemanticsNodeInteractionsProvider.assertBackPress(){
    Espresso.pressBack()
}
fun SemanticsNodeInteractionsProvider.assertMovieListItemClickCheck(movies:List<Movie>){
    val movie = movies.last()
    onNodeWithTag("MoviesList").assertExists()  //list view available

    onNodeWithTag("MoviesList").performTouchInput {
        swipeUp()
    }

    onNodeWithTag("${movie.id}").apply {
        assertExists()
        performClick()
    }
}


//Details Screen
fun SemanticsNodeInteractionsProvider.assertMovieDetailsCheck(movie: Movie){
    with(movie) {

        onNodeWithTag("ImageSlider")
            .assertIsDisplayed()

        onNodeWithText(name)
            .assertIsDisplayed()

        onNodeWithText("Rating : $rating ($ratingCount) | ")
            .assertIsDisplayed()

        onNodeWithText(status)
            .assertIsDisplayed()

        onNodeWithText(description!!)
            .assertIsDisplayed()

    }
}

fun SemanticsNodeInteractionsProvider.asserMovieDetailsCheck(movie: Movie) {
    //given
    val galleryContainer = onNode(hasTestTag("ImageSlider"))
    galleryContainer.assertIsDisplayed()
    movie.pictures?.forEachIndexed { index, _ ->
        galleryContainer.performScrollToNode(hasContentDescription("Movie Image $index"))
    }
}

fun SemanticsNodeInteractionsProvider.asserMovieDetailsPlayCheck(movie: Movie) {
    val playButton = onNode(hasText("Play"))
    playButton.assertIsDisplayed()
    playButton.performClick()
}

fun SemanticsNodeInteractionsProvider.asserMovieDetailsDownloadCheck() {
    val downloadButton = onNode(hasText("Download"))
    downloadButton.assertIsDisplayed()
    downloadButton.performClick()
}

fun SemanticsNodeInteractionsProvider.asserMovieDetailsScrollCheck() {
    onRoot().performTouchInput {
        swipeUp(durationMillis = 400)
    }
}

fun SemanticsNodeInteractionsProvider.assertCheckNavigation(){
    assertCheckNavigation()
}