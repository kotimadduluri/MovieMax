package com.moviemax.model.movie.repository

import com.moviemax.model.Resource
import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.remote.model.MoviesResponse

class MovieRepositoryImp(private val movieApi: MovieApi) : MovieRepository {
    override suspend fun getMovies(page: Int): Resource<MoviesResponse> {
        return try {
            val response = movieApi.getMovies(page)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage)
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Resource<MovieDetailsResponse> {
        return try {
            Resource.Success(movieApi.getMovieDetails(movieId))
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage)
        }
    }
}