package com.moviemax.viewmodel

import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth
import com.moviemax.UiText
import com.moviemax.common.BaseTest
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.fake.getMovieDetailsResponseTestWithError
import com.moviemax.fake.movieId
import com.moviemax.model.Resource
import com.moviemax.model.asSuccess
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.toMovie
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.view.movie.UiState
import com.moviemax.view.movie.UiState.Loading.asError
import com.moviemax.view.movie.UiState.Loading.asSuccess
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
    private lateinit var fakeMovieRepository: FakeMovieRepository
    private lateinit var viewModel: MovieDetailsScreenViewModel

    override fun initRequiredDependencies() {
        //Given
        fakeMovieRepository = FakeMovieRepository(networkReader)
        every { networkReader.isInternetAvailable() } returns true
        viewModel = initVM()
    }


    @Test
    fun `onAction() with GetDetails on success`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MovieDetailsScreenIntent.GetDetails(movieId))

        //Assert
        val stateValue = viewModel.uiState().value

        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)
            val data = stateValue.asSuccess<Movie>().data
            val fakeData = fakeResponse.asSuccess<MovieDetailsResponse>().data?.tvShow?.toMovie()
            Truth.assertThat(data).isEqualTo(fakeData)
        }
    }

    @Test
    fun `onAction() with GetDetails on error`() = runTest {
        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MovieDetailsScreenIntent.GetDetails(movieId))

        //Assert
        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            val errorMessage = this[2].asError().message
            Truth.assertThat(errorMessage).isInstanceOf(UiText::class.java)
        }
    }


    @Test
    fun `onAction() with Refresh on success`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MovieDetailsScreenIntent.Refresh(movieId))

        //Assert
        val stateValue = viewModel.uiState().value

        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)
            val data = stateValue.asSuccess<Movie>().data
            val fakeData = fakeResponse.asSuccess<MovieDetailsResponse>().data?.tvShow?.toMovie()
            Truth.assertThat(data).isEqualTo(fakeData)
        }
    }

    @Test
    fun `onAction() with Refresh on error`() = runTest {
        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MovieDetailsScreenIntent.Refresh(movieId))

        //Assert
        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            val errorMessage = this[2].asError().message
            Truth.assertThat(errorMessage).isInstanceOf(UiText::class.java)
        }
    }

    @Test
    fun `getMovieDetails() success`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        val stateValue = viewModel.uiState().value

        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)
            val data = stateValue.asSuccess<Movie>().data
            val fakeData = fakeResponse.asSuccess<MovieDetailsResponse>().data?.tvShow?.toMovie()
            Truth.assertThat(data).isEqualTo(fakeData)
        }
    }

    @Test
    fun `getMovieDetails() api success but return empty data`() = runTest {
        //Given
        val fakeData =
            Resource.Success<MovieDetailsResponse>(getMovieDetailsResponseTestWithError(movieId))
        coEvery { movieDetailsUseCase(movieId) } returns fakeData

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        val stateValue = viewModel.uiState().value

        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)
            val data = stateValue.asError().message
            Truth.assertThat(data).isNotNull()
            Truth.assertThat(data).isInstanceOf(UiText::class.java)
        }
    }

    @Test
    fun `getMovieDetails() when network error comes`() = runTest {
        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            val errorMessage = this[2].asError().message
            Truth.assertThat(errorMessage).isInstanceOf(UiText::class.java)
        }
    }

    @Test
    fun `getMovieDetails() when generic error occurs`() = runTest {
        //Given
        coEvery { movieDetailsUseCase(movieId) } returns Resource.Error()

        val states = observeStates()

        //When
        viewModel.getMovieDetails(movieId)

        //Assert
        with(states) {
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isEqualTo(UiState.None)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)
        }
    }

    private fun initVM() = MovieDetailsScreenViewModel(
        movieDetailsUseCase = movieDetailsUseCase
    )

    private fun observeStates(): MutableList<UiState> {
        val states = mutableListOf<UiState>()
        viewModel.uiState().asLiveData().observeForever { state ->
            states.add(state)
        }
        return states
    }

}