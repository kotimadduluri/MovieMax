package com.moviemax.viewmodel

import androidx.lifecycle.viewModelScope
import com.moviemax.R
import com.moviemax.UiText
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
import com.moviemax.model.movie.data.toMovie
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.view.movie.UiState
import kotlinx.coroutines.launch

class MovieDetailsScreenViewModel(
    private val movieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<UiState>(UiState.None) {


    fun onAction(intent: MovieDetailsScreenIntent) {
        when (intent) {
            is MovieDetailsScreenIntent.GetDetails -> {
                getMovieDetails(intent.movieId)
            }
            is MovieDetailsScreenIntent.Refresh -> {
                getMovieDetails(intent.movieId)
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val networkResponse = movieDetailsUseCase(movieId)
            if (networkResponse is Resource.Success<*>) {
                val response = networkResponse as Resource.Success<MovieDetailsResponse>
                uiState.value = response.data?.tvShow?.toMovie()?.let { movie ->
                        UiState.Success(movie)
                    } ?: UiState.Error(UiText.StringResource(R.string.error_details_not_found))
            } else {
                uiState.value = UiState.Error(networkResponse.message)
            }
        }
    }

}

sealed class MovieDetailsScreenIntent {
    data class GetDetails(val movieId: Int) : MovieDetailsScreenIntent()
    data class Refresh(val movieId: Int) : MovieDetailsScreenIntent()
}