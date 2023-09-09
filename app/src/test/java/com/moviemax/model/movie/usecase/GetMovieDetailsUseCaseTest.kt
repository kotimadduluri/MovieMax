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
class GetMovieDetailsUseCaseTest : BaseTest() {

    @MockK
    lateinit var movieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository

    override fun initRequiredDependencies() {
        fakeMovieRepository = FakeMovieRepository(networkReader)
    }

    @Test
    fun `GetMovieDetailsUseCase when success`() = runTest {
        //given
        val movieId = 29561
        every { networkReader.isInternetAvailable() } returns true
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        //when
        val response = movieDetailsUseCase(movieId)

        //assert
        Truth.assertThat(response).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat(response).isEqualTo(fakeResponse)
    }

    @Test
    fun `GetMovieDetailsUseCase when error throws`() = runTest {
//Given
        val movieId = 29561
        every { networkReader.isInternetAvailable() } returns false
        val fakeResponse = fakeMovieRepository.getMoviesDetails(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns fakeResponse

        //when
        val response = movieDetailsUseCase(movieId)

        //assert
        Truth.assertThat(response).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(response.message).isNotNull()
    }
}