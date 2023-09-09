package com.moviemax.model.movie.usecase

import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.fake.getMovieDetailsResponseTest
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailsUseCaseTest : BaseTest() {

    @MockK
    lateinit var movieDetailsUseCase: GetMovieDetailsUseCase

    override fun initRequiredDependencies() {
        //not required
    }

    @Test
    fun `GetMovieDetailsUseCase when success`()= runTest{
        //given
        val movieId = 29561
        val movieDetailsResponse = getMovieDetailsResponseTest(movieId)
        coEvery { movieDetailsUseCase(movieId) } returns Resource.Success<MovieDetailsResponse>(
            movieDetailsResponse
        )

        //when
        val response = movieDetailsUseCase(movieId)

        //assert
        Truth.assertThat(response.status).isInstanceOf(Resource.STATUS.SUCCESS::class.java)
        Truth.assertThat(movieDetailsResponse).isEqualTo((response as Resource.Success<MovieDetailsResponse>).data)
    }

    @Test
    fun `GetMovieDetailsUseCase when error throws`()= runTest{
//Given
        val error ="ERROR"
        val movieId = 29561
        coEvery { movieDetailsUseCase(movieId) } returns Resource.Error(message = error)

        //when
        val response = movieDetailsUseCase(movieId)

        //assert
        Truth.assertThat(response.status).isInstanceOf(Resource.STATUS.ERROR::class.java)
        Truth.assertThat(response.message).isEqualTo(error)
    }
}