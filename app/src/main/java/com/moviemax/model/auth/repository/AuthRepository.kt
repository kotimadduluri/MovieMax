package com.moviemax.model.auth.repository

import com.moviemax.model.Resource
import com.moviemax.model.auth.data.User

interface AuthRepository {
    suspend fun login(email: String, password: String):Resource
    suspend fun registration(
        user: User
    ):Resource
}