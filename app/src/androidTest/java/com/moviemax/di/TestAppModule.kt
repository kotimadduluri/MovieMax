package com.moviemax.di

import com.moviemax.fake.FakeMovieRepository
import com.moviemax.fake.FakeMoviesApi
import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.repository.MovieRepository
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.viewmodel.MovieDetailsScreenViewModel
import com.moviemax.viewmodel.MoviesScreenViewModel
import com.network.reader.NetworkReader
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val TestCommonTestModule = module {
    single<NetworkReader> {
        mockWithNetwork()
    }

    singleOf(::FakeMoviesApi) bind MovieApi::class

    singleOf(::FakeMovieRepository) bind MovieRepository::class
}

val TestMoviesListModule = module {
    singleOf(::GetMoviesUseCase)
    viewModelOf(::MoviesScreenViewModel)
}

val TestMovieDetailsModule = module {
    singleOf(::GetMovieDetailsUseCase)
    viewModelOf(::MovieDetailsScreenViewModel)
}

fun mockNoNetwork():NetworkReader=NetworkReader { false }
fun mockWithNetwork():NetworkReader=NetworkReader { true }


