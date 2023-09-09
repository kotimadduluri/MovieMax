package com.moviemax.viewmodel

import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.common.castAsError
import com.moviemax.common.castAsSuccess
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.view.movie.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MoviesScreenViewModelTest : BaseTest(){

    @MockK
    lateinit var moviesUseCase: GetMoviesUseCase

    private val fakeMovieRepository = FakeMovieRepository()

    private lateinit var viewModel: MoviesScreenViewModel

    override fun initRequiredDependencies() {
        runTest {
            coEvery { moviesUseCase(1) } returns fakeMovieRepository.getMovies(1)
        }
        viewModel = initVM()
    }

    @Test
    fun `getMovieDetails()_Success_with_default_page_loading`() = runTest {
        //Given
        every { networkReader.isInternetAvailable()} returns  true
        coEvery { moviesUseCase(1) } returns fakeMovieRepository.getMovies(1)

        //Assert
        val stateValue = viewModel.uiState().value
        Truth.assertThat(stateValue).isInstanceOf(UiState.Success::class.java)

        val movies = stateValue.castAsSuccess<List<Movie>>().data
        Truth.assertThat(movies).isNotNull()
        Truth.assertThat(movies).isNotEmpty()
    }

    @Test
    fun `getMovieDetails()_success_on_manual_request`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMovies(1)
        every { networkReader.isInternetAvailable()} returns  true
        coEvery { moviesUseCase(1) } returns fakeResponse


        //When
        viewModel.getMovies(1)

        //Assert
        val stateValue = viewModel.uiState().value
        Truth.assertThat(stateValue).isInstanceOf(UiState.Success::class.java)

        val movies = stateValue.castAsSuccess<List<Movie>>().data
        Truth.assertThat(movies).isNotNull()
        Truth.assertThat(movies).isNotEmpty()
    }

    @Test
    fun `getMovieDetails()_success_with_zero_records_error`() = runTest {
        //Given
        val fakeResponse = MoviesResponse(
            total = "0",
            page = 0,
            pages = 0,
            tvShows = null
        )

        coEvery { moviesUseCase(1) } returns Resource.Success<MoviesResponse>(fakeResponse)

        //When
        viewModel.getMovies(1)

        //Assert
        val stateValue = viewModel.uiState().value
        Truth.assertThat(stateValue).isInstanceOf(UiState.Error::class.java)
    }

    @Test
    fun `getMovieDetails()_Error`() = runTest {
        //Given
        every { networkReader.isInternetAvailable()} returns  false
        val error = "error"
        coEvery { moviesUseCase(1) } returns Resource.Error(message = error)

        //When
        viewModel.getMovies(1)

        //Assert
        val stateValue = viewModel.uiState().value
        val response = stateValue.castAsError()
        Truth.assertThat(response).isInstanceOf(UiState.Error::class.java)
        Truth.assertThat(response.message).isEqualTo(error)
    }

    private fun initVM() = MoviesScreenViewModel(
        moviesUseCase = moviesUseCase
    )

}