package com.moviemax.viewmodel

import androidx.lifecycle.viewModelScope
import com.common.R
import com.common.util.UiText
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.domain.model.Movie
import com.moviemax.model.movie.data.getMovies
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.view.movie.Destination
import com.moviemax.view.movie.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesScreenViewModel(
    private val moviesUseCase: GetMoviesUseCase,
) : BaseViewModel<UiState>(UiState.None) {

    private val currentPage = MutableStateFlow(1)

    init {
        onAction(MoviesScreenIntent.GetMovies(currentPage.value))
    }

    fun onAction(intent: MoviesScreenIntent,navigate:(route:String)->Unit ={}) {
        when (intent) {
            is MoviesScreenIntent.Refresh -> {
                getMovies(1) //to start from page 1
            }

            is MoviesScreenIntent.GetMovies -> {
                getMovies(intent.page)
            }

            is MoviesScreenIntent.ViewDetails ->{
                navigate(Destination.Details.createRoute(intent.movie.id))
            }
        }
    }

    fun getMovies(page: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val networkResponse = moviesUseCase(page)
            if (networkResponse is Resource.Success<*>) {
                val response = networkResponse as Resource.Success<MoviesResponse>
                with(response.data?.getMovies()) {
                    if (isNullOrEmpty()) {
                        uiState.value = UiState.Error(UiText.StringResource(R.string.error_no_records))
                    } else {
                        currentPage.value = page
                        uiState.value = UiState.Success(this)
                    }
                }
            } else {
                uiState.value = UiState.Error(networkResponse.message)
            }
        }
    }
}

sealed class MoviesScreenIntent {
    data class GetMovies(val page: Int) : MoviesScreenIntent()
    data class ViewDetails(val movie: Movie) : MoviesScreenIntent()
    object Refresh : MoviesScreenIntent()
}