package com.moviemax.viewmodel

import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.common.castAsError
import com.moviemax.common.castAsSuccess
import com.moviemax.fake.FAKE_NETWORK_ERROR
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.fake.getMovieDetailsResponseTestWithError
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.toMovie
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.view.movie.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsScreenViewModelTest : BaseTest() {

    @MockK
    lateinit var movieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var fakeMovieRepository : FakeMovieRepository
    private lateinit var viewModel: MovieDetailsScreenViewModel

    override fun initRequiredDependencies() {
        //Given
        fakeMovieRepository = FakeMovieRepository(networkReader)
        every { networkReader.isInternetAvailable() } returns true
        viewModel = initVM()
    }

    @Test
    fun `getMovieDetails()_success`() = runTest {
        //Given
        val movieId = 29561
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        val fakeData = fakeResponse.castAsSuccess<MovieDetailsResponse>().data?.tvShow?.toMovie()
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        val stateValue = viewModel.uiState().value

        with(states){
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)
            val data =  stateValue.castAsSuccess<Movie>().data
            Truth.assertThat(data).isEqualTo(fakeData)
        }
    }

    @Test
    fun `getMovieDetails()_success_with_no_data`() = runTest {
        //Given
        val movieId = 29561
        val fakeData = Resource.Success<MovieDetailsResponse>(getMovieDetailsResponseTestWithError(movieId))
        coEvery { movieDetailsUseCase(movieId) } returns fakeData

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        val stateValue = viewModel.uiState().value

        with(states){
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)
            val data =  stateValue.castAsError().message
            Truth.assertThat(data).isNotNull()
            Truth.assertThat(data).isNotEmpty()
        }
    }

    @Test
    fun `getMovieDetails()_error_network`() = runTest {
        //Given
        val movieId = 29561
        coEvery { movieDetailsUseCase(movieId) } returns Resource.Error(message = FAKE_NETWORK_ERROR)

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        with(states){
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            val errorMessage=  this[2].castAsError().message
            Truth.assertThat(errorMessage).isEqualTo(FAKE_NETWORK_ERROR)
        }
    }

    @Test
    fun `getMovieDetails()_error_generic`() = runTest {
        //Given
        val movieId = 29561
        val error = "error"
        coEvery { movieDetailsUseCase(movieId) } returns Resource.Error(message = error)

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        with(states){
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)
        }
    }

    private fun initVM()= MovieDetailsScreenViewModel(
        movieDetailsUseCase = movieDetailsUseCase
    )

    private fun observeStates(): MutableList<UiState> {
        val states = mutableListOf<UiState>()
        viewModel.uiState().asLiveData().observeForever { state->
            states.add(state)
        }
        return states
    }

}