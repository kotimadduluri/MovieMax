package com.moviemax.model.auth.usecase

import com.moviemax.model.Resource
import com.moviemax.model.auth.repository.AuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginUseCase :KoinComponent {
    private val authRepository:AuthRepository by inject()
    suspend operator fun invoke(
        email:String,password:String
    ):Resource{
        return authRepository.login(email,password)
    }
}