package com.moviemax.model.movie.usecase

import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.model.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseTest : BaseTest() {

    @MockK
    lateinit var moviesUseCase: GetMoviesUseCase
    private lateinit var fakeMovieRepository : FakeMovieRepository

    override fun initRequiredDependencies() {
        fakeMovieRepository = FakeMovieRepository(networkReader)
    }

    @Test
    fun `GetMoviesUseCase with success response`() = runTest {

        //given
        every { networkReader.isInternetAvailable() } returns true
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        //when
        val response = moviesUseCase(1)

        //assert
        Truth.assertThat(response).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat(response).isEqualTo(fakeResponse)
    }

    @Test
    fun `GetMoviesUseCase with error`() = runTest {

        //Given
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMovies(1)
        coEvery { moviesUseCase(1) } returns fakeResponse

        //when
        val response = moviesUseCase(1)

        //assert
        Truth.assertThat(response).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(response.message).isNotNull()
    }

}