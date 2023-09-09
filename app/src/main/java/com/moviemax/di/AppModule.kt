package com.moviemax.di

import com.moviemax.BuildConfig
import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.repository.MovieRepository
import com.moviemax.model.movie.repository.MovieRepositoryImp
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.viewmodel.MovieDetailsScreenViewModel
import com.moviemax.viewmodel.MoviesScreenViewModel
import com.network.client.DomainConfiguration
import com.network.client.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    single {
        DomainConfiguration {
            scheme = "https"
            host = BuildConfig.APP_DOMAIN
            logEnabled = BuildConfig.DEBUG
        }
    }

    single<MovieApi> {
        get<NetworkClient>().buildApi(MovieApi::class.java)
    }

    single {
        GetMoviesUseCase()
    }

    single<MovieRepository> {
        MovieRepositoryImp(get(),get())
    }

    single {
        GetMovieDetailsUseCase()
    }

    viewModel {
        MoviesScreenViewModel(get())
    }

    viewModel {
        MovieDetailsScreenViewModel(get())
    }
}