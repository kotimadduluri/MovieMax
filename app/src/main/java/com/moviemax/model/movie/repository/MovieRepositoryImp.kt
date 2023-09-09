package com.moviemax.model.movie.repository

import com.moviemax.R
import com.moviemax.UiText
import com.moviemax.model.Resource
import com.moviemax.model.movie.MovieApi
import com.network.reader.NetworkReader

class MovieRepositoryImp(
    private val movieApi: MovieApi,
    private val networkReader: NetworkReader
) : MovieRepository {
    override suspend fun getMovies(page: Int): Resource {
        return try {
            if (networkReader.isInternetAvailable()) {
                val response = movieApi.getMovies(page)
                Resource.Success(response)
            } else Resource.Error(message = UiText.StringResource(R.string.error_network))
        } catch (e: Exception) {
            Resource.Error(message = UiText.PlainString(e.localizedMessage))
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Resource {
        return try {
            if (networkReader.isInternetAvailable()) {
                Resource.Success(movieApi.getMovieDetails(movieId))
            } else Resource.Error(message = UiText.StringResource(R.string.error_network))
        } catch (e: Exception) {
            Resource.Error(message = UiText.PlainString(e.localizedMessage))
        }
    }
}