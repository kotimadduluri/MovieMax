package com.moviemax.di

import com.moviemax.BuildConfig
import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.repository.MovieRepository
import com.moviemax.model.movie.repository.MovieRepositoryImp
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.viewmodel.MoviesScreenViewModel
import com.network.client.DomainConfiguration
import com.network.client.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    single {
        DomainConfiguration {
            host = BuildConfig.APP_DOMAIN
        }
    }

    single<MovieApi> {
        get<NetworkClient>().buildApi(MovieApi::class.java)
    }

    single {
        GetMoviesUseCase()
    }

    single<MovieRepository> {
        MovieRepositoryImp(get())
    }

    single {
        GetMovieDetailsUseCase()
    }

    viewModel {
        MoviesScreenViewModel(get())
    }
}