package com.moviemax.viewmodel

import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.common.castAsError
import com.moviemax.common.castAsSuccess
import com.moviemax.fake.FAKE_NETWORK_ERROR
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.fake.getFakeMoviesTestWithError
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.view.movie.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesScreenViewModelTest : BaseTest() {

    @MockK
    lateinit var moviesUseCase: GetMoviesUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository
    private lateinit var viewModel: MoviesScreenViewModel

    override fun initRequiredDependencies() {
        fakeMovieRepository = FakeMovieRepository(networkReader)
        every { networkReader.isInternetAvailable() } returns true
        runTest {
            coEvery { moviesUseCase(1) } returns fakeMovieRepository.getMovies(1)
        }
        viewModel = initVM()
    }

    @Test
    fun `getMovieDetails()_success_with_default_page_loading`() = runTest {
        //Given
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
        coEvery { moviesUseCase(1) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.getMovies(1)

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java) //from
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)

            //data check
            val movies = this[2].castAsSuccess<List<Movie>>().data
            Truth.assertThat(movies).isNotNull()
            Truth.assertThat(movies).isNotEmpty()
        }
    }

    @Test
    fun `getMovieDetails()_success_with_zero_records_error`() = runTest {
        //Given
        val fakeResponse = getFakeMoviesTestWithError()
        coEvery { moviesUseCase(1) } returns Resource.Success<MoviesResponse>(fakeResponse)
        val states = observeStates()

        //When
        viewModel.getMovies(1)

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            //data check
            val movies = this[2].castAsError()
            Truth.assertThat(movies.message).isNotNull()
            Truth.assertThat(movies.message).isNotEmpty()
        }
    }

    @Test
    fun `getMovieDetails()_error_generic`() = runTest {
        //Given
        val error = "error"
        coEvery { moviesUseCase(1) } returns Resource.Error(message = error)
        val states = observeStates()

        //When
        viewModel.getMovies(1)

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            //data check
            val movies = this[2].castAsError()
            Truth.assertThat(movies.message).isNotNull()
            Truth.assertThat(movies.message).isEqualTo(error)
        }
    }

    @Test
    fun `getMovieDetails()_error_network`() = runTest {
        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse
        val states = observeStates()

        //When
        viewModel.getMovies(1)

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java)
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            //data check
            val movies = this[2].castAsError()
            Truth.assertThat(movies.message).isNotNull()
            Truth.assertThat(movies.message).isEqualTo(FAKE_NETWORK_ERROR)
        }
    }

    private fun initVM() = MoviesScreenViewModel(
        moviesUseCase = moviesUseCase
    )

    private fun observeStates(): MutableList<UiState> {
        val states = mutableListOf<UiState>()
        viewModel.uiState().asLiveData().observeForever { state ->
            states.add(state)
        }
        return states
    }

}