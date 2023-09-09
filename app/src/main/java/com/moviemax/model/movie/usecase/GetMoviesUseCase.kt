package com.moviemax.model.movie.usecase

import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMoviesUseCase : KoinComponent {
    private val repository : MovieRepository by inject()
    suspend operator fun invoke(
        page:Int
    ): Resource {
        return repository.getMovies(page)
    }
}