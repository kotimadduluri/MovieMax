package com.moviemax.model.auth.usecase

import com.moviemax.model.Resource
import com.moviemax.model.auth.data.User
import com.moviemax.model.auth.repository.AuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegistrationUseCase : KoinComponent {
    private val authRepository: AuthRepository by inject()
    suspend operator fun invoke(
        user: User
    ):Resource{
        return authRepository.registration(user)
    }
}