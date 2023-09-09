package com.moviemax.model.movie.usecase

import com.google.common.truth.Truth
import com.moviemax.common.BaseTest
import com.moviemax.fake.getMovieResponseTest
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest

import org.junit.Test

class GetMoviesUseCaseTest : BaseTest() {

    @MockK
    lateinit var moviesUseCase: GetMoviesUseCase

    override fun initRequiredDependencies() {
        //nothing required
    }

    @Test
    fun `GetMoviesUseCase_Success`() = runTest {

        //given
        coEvery { moviesUseCase(1) } returns Resource.Success<MoviesResponse>(getMovieResponseTest())

        //when
        val response = moviesUseCase(1)

        //assert
        Truth.assertThat(response.status).isInstanceOf(Resource.STATUS.SUCCESS::class.java)
    }

    @Test
    fun `GetMoviesUseCase_error`() = runTest {

        //Given
        val error ="ERROR"
        coEvery { moviesUseCase(1) } returns Resource.Error(message = error)

        //when
        val response = moviesUseCase(1)

        //assert
        Truth.assertThat(response.status).isInstanceOf(Resource.STATUS.ERROR::class.java)
        Truth.assertThat(response.message).isEqualTo(error)
    }

}