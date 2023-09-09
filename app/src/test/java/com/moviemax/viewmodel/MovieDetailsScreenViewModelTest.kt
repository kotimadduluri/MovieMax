package com.moviemax.viewmodel

import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.view.movie.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest

import org.junit.Test

class MovieDetailsScreenViewModelTest : BaseTest() {

    @MockK
    lateinit var movieDetailsUseCase: GetMovieDetailsUseCase
    private val fakeMovieRepository = FakeMovieRepository()
    private lateinit var viewModel: MovieDetailsScreenViewModel

    override fun initRequiredDependencies() {
        //Given
        viewModel = MovieDetailsScreenViewModel(
            movieDetailsUseCase = movieDetailsUseCase
        )
    }

    @Test
    fun `getMovieDetails()_Success`() = runTest {
        //Given
        val movieId = 29561
        every { networkReader.isInternetAvailable() } returns true
        coEvery { movieDetailsUseCase(movieId) } returns fakeMovieRepository.getMoviesDetails(
            movieId
        )

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        val stateValue = viewModel.uiState().value
        Truth.assertThat(stateValue).isInstanceOf(UiState.Success::class.java)
        Truth.assertThat(movieId).isEqualTo((stateValue as UiState.Success<Movie>).data.id)
    }

    @Test
    fun `getMovieDetails()_general_error`() = runTest {
        //Given
        val movieId = 29561
        val error = "error"
        coEvery { movieDetailsUseCase(movieId) } returns Resource.Error(message = error)

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        val stateValue = viewModel.uiState().value
        Truth.assertThat(stateValue).isInstanceOf(UiState.Error::class.java)
        Truth.assertThat(error).isEqualTo((stateValue as UiState.Error).message)
    }
}