package com.moviemax.viewmodel

import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.fake.getFakeMoviesTestWithError
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.getFakeMovie
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.view.movie.Destination
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
    fun `onInit() success`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MoviesScreenIntent.GetMovies(1))

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java) //from
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)

            //data check
            val movies = this[2].asSuccess<List<Movie>>().data
            Truth.assertThat(movies).isNotNull()
            Truth.assertThat(movies).isNotEmpty()
        }
    }

    @Test
    fun `onAction() error`() = runTest {
        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MoviesScreenIntent.GetMovies(1))

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java) //from
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            //data check
            val error = this[2].asError()
            Truth.assertThat(error.message).isNotNull()
        }
    }

    @Test
    fun `onAction() with refresh when success`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MoviesScreenIntent.Refresh)

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java) //from
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)

            //data check
            val movies = this[2].asSuccess<List<Movie>>().data
            Truth.assertThat(movies).isNotNull()
            Truth.assertThat(movies).isNotEmpty()
        }
    }

    @Test
    fun `onAction() with refresh when error`() = runTest {
        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MoviesScreenIntent.Refresh)

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java) //from
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Error::class.java)

            //data check
            val error = this[2].asError()
            Truth.assertThat(error.message).isNotNull()
        }
    }

    @Test
    fun `ViewDetails() should route to details screen`() = runTest {
        //Given
        val movieId = 29561
        val movie = getFakeMovie(movieId)!!
        val fakeRouter=Destination.Details.createRoute(movieId)

        //When
        var resultRouter:String? = null
        viewModel.onAction(MoviesScreenIntent.ViewDetails(movie)){router->
            resultRouter = router
        }

        //Assertion
        Truth.assertThat(resultRouter).isNotNull()
        Truth.assertThat(resultRouter).isEqualTo(fakeRouter)
    }

    @Test
    fun `getMovieDetails() init method loads data successfully`() = runTest {
        //Given
        coEvery { moviesUseCase(1) } returns fakeMovieRepository.getMovies(1)

        //Assert
        val stateValue = viewModel.uiState().value
        Truth.assertThat(stateValue).isInstanceOf(UiState.Success::class.java)

        val movies = stateValue.asSuccess<List<Movie>>().data
        Truth.assertThat(movies).isNotNull()
        Truth.assertThat(movies).isNotEmpty()
    }

    @Test
    fun `getMovieDetails() success on user request`() = runTest {
        //Given
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        val states = observeStates()

        //When
        viewModel.onAction(MoviesScreenIntent.GetMovies(1))

        //Assertion
        with(states) {
            //state check
            Truth.assertThat(size).isEqualTo(3)
            Truth.assertThat(this[0]).isInstanceOf(UiState.Success::class.java) //from
            Truth.assertThat(this[1]).isEqualTo(UiState.Loading)
            Truth.assertThat(this[2]).isInstanceOf(UiState.Success::class.java)

            //data check
            val movies = this[2].asSuccess<List<Movie>>().data
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
            val movies = this[2].asError()
            Truth.assertThat(movies.message).isNotNull()
        }
    }

    @Test
    fun `getMovieDetails()_error_generic`() = runTest {
        //Given
        coEvery { moviesUseCase(1) } returns Resource.Error()
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
            val movies = this[2].asError()
            Truth.assertThat(movies.message).isNotNull()
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
            val movies = this[2].asError()
            Truth.assertThat(movies.message).isNotNull()
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