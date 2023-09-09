package com.moviemax.model.movie.usecase

import com.moviemax.model.Resource
import com.moviemax.model.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//use-case to get movie details by respective id
class GetMovieDetailsUseCase : KoinComponent {
    private val repository: MovieRepository by inject()
    suspend operator fun invoke(movieId: Int): Resource {
        return repository.getMoviesDetails(movieId)
    }
}