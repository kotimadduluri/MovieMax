package com.moviemax.fake

import com.common.util.UiText
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.repository.MovieRepository
import com.network.reader.NetworkReader

const val FAKE_NETWORK_ERROR = "ERROR"

class FakeMovieRepository(
    private val networkReader: NetworkReader
) : MovieRepository {
    override suspend fun getMovies(page: Int): Resource {
        return if (networkReader.isInternetAvailable()) {
            Resource.Success<MoviesResponse>(getMovieResponseTest())
        } else Resource.Error(message = UiText.PlainString(FAKE_NETWORK_ERROR))
    }

    override suspend fun getMoviesDetails(movieId: Int): Resource {
        return if (networkReader.isInternetAvailable()) {
            return Resource.Success(getMovieDetailsResponseTest(movieId))
        } else Resource.Error(message = UiText.PlainString(FAKE_NETWORK_ERROR))
    }
}