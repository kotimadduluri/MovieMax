package com.moviemax.model.movie.repository

import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.common.castAsError
import com.moviemax.common.castAsSuccess
import com.moviemax.fake.FakeMoviesApi
import com.moviemax.model.Resource
import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImpTest : BaseTest() {

    @MockK
    lateinit var movieRepositoryImp: MovieRepositoryImp
    @MockK
    lateinit var moviesApi: MovieApi

    private val fakeMoviesApi = FakeMoviesApi()
    override fun initRequiredDependencies() {
        //Given
        movieRepositoryImp = MovieRepositoryImp(
            moviesApi,
            networkReader
        )
    }

    @Test
    fun `getMovies_when_success`() = runTest {

        //given
        val fakeResponse = fakeMoviesApi.getMovies(1)
        every { networkReader.isInternetAvailable() } returns true
        coEvery { moviesApi.getMovies(1) } returns fakeResponse

        //when
        val movieResponse=movieRepositoryImp.getMovies(1)

        //assertion
        Truth.assertThat(movieResponse).isInstanceOf(Resource.Success::class.java)

        val successResponse= movieResponse.castAsSuccess<MoviesResponse>().data
        Truth.assertThat(successResponse).isInstanceOf(MoviesResponse::class.java)
        Truth.assertThat(successResponse).isEqualTo(fakeResponse)
    }

    @Test
    fun `getMovies_when_error`() = runTest {

        //given
        every { networkReader.isInternetAvailable() } returns false

        //when
        val movieResponse=movieRepositoryImp.getMovies(1)

        //assertion
        Truth.assertThat(movieResponse).isInstanceOf(Resource.Error::class.java)

        val error= movieResponse.castAsError()
        Truth.assertThat(error.status).isEqualTo(Resource.STATUS.ERROR)
        Truth.assertThat(error.message).isNotNull()
    }

    @Test
    fun `getMoviesDetails_when_success`()= runTest {

        //given
        val movieId = 29561
        val fakeResponse = fakeMoviesApi.getMovieDetails(movieId)
        every { networkReader.isInternetAvailable() } returns true
        coEvery { moviesApi.getMovieDetails(movieId) } returns fakeResponse

        //when
        val movieResponse=movieRepositoryImp.getMoviesDetails(movieId)

        //assertion
        Truth.assertThat(movieResponse).isInstanceOf(Resource.Success::class.java)

        val successResponse= movieResponse.castAsSuccess<MovieDetailsResponse>().data
        Truth.assertThat(successResponse).isInstanceOf(MovieDetailsResponse::class.java)
        Truth.assertThat(successResponse).isEqualTo(fakeResponse)
    }

    @Test
    fun `getMoviesDetails_when_error`() = runTest {

        //given
        val movieId = 29561
        every { networkReader.isInternetAvailable() } returns false

        //when
        val movieResponse=movieRepositoryImp.getMoviesDetails(movieId)

        //assertion
        Truth.assertThat(movieResponse).isInstanceOf(Resource.Error::class.java)

        val error= movieResponse.castAsError()
        Truth.assertThat(error.status).isEqualTo(Resource.STATUS.ERROR)
        Truth.assertThat(error.message).isNotNull()
    }
}