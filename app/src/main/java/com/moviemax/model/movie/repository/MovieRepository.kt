package com.moviemax.model.movie.repository

import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.remote.model.MoviesResponse

interface MovieRepository {
    suspend fun getMovies(page: Int): Resource<MoviesResponse>
    suspend fun getMoviesDetails(movieId: Int): Resource<MovieDetailsResponse>
}