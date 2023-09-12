package com.moviemax.di

import com.moviemax.fake.FakeAuthRepositoryImp
import com.moviemax.fake.FakeMovieRepository
import com.moviemax.fake.FakeMoviesApi
import com.moviemax.model.movie.MovieApi
import com.moviemax.model.movie.repository.MovieRepository
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.model.auth.usecase.LoginUseCase
import com.moviemax.model.auth.usecase.RegistrationUseCase
import com.moviemax.model.auth.repository.AuthRepository
import com.moviemax.viewmodel.auth.AuthViewModel
import com.moviemax.view.auth.router.AuthRouter
import com.moviemax.view.auth.router.AuthRouterImp
import com.moviemax.viewmodel.movie.MovieDetailsScreenViewModel
import com.moviemax.viewmodel.movie.MoviesScreenViewModel
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

val TestAuthModule = module {
    singleOf(::AuthRouterImp) bind AuthRouter::class
    singleOf(::FakeAuthRepositoryImp) bind AuthRepository::class
    singleOf(::LoginUseCase)
    singleOf(::RegistrationUseCase)
    viewModelOf(::AuthViewModel)
}

fun mockNoNetwork():NetworkReader=NetworkReader { false }
fun mockWithNetwork():NetworkReader=NetworkReader { true }


