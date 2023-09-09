package com.moviemax.fake

import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.repository.MovieRepository
import kotlinx.coroutines.delay

const val FAKE_NETWORK_ERROR ="ERROR"
class FakeMovieRepository : MovieRepository {


    override suspend fun getMovies(page: Int): Resource {
        return Resource.Success<MoviesResponse>(getMovieResponseTest())
    }

    override suspend fun getMoviesDetails(movieId: Int): Resource {
        return Resource.Success(getMovieDetailsResponseTest(movieId))
    }
}