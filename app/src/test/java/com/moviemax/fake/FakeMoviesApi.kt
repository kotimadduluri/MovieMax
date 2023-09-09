package com.moviemax.fake

import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.remote.model.MoviesResponse

class FakeMoviesApi : MovieApi {
    override suspend fun getMovies(page: Int): MoviesResponse = getMovieResponseTest()

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse =
        getMovieDetailsResponseTest(movieId)
}