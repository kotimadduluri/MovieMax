package com.moviemax.di

import com.moviemax.model.auth.repository.AuthRepository
import com.moviemax.model.auth.repository.AuthRepositoryImp
import com.moviemax.model.auth.usecase.LoginUseCase
import com.moviemax.model.auth.usecase.RegistrationUseCase
import com.moviemax.view.auth.router.AuthRouter
import com.moviemax.view.auth.router.AuthRouterImp
import com.moviemax.viewmodel.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val DI_AuthModule = module {

    singleOf(::AuthRouterImp) bind AuthRouter::class

    singleOf(::AuthRepositoryImp) bind AuthRepository::class

    singleOf(::LoginUseCase)

    singleOf(::RegistrationUseCase)

    viewModelOf(::AuthViewModel)

}