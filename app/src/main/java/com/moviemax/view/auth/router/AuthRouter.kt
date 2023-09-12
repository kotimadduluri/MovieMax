package com.moviemax.view.auth.router

import androidx.navigation.NavController

interface AuthRouter {
    fun onLoginCompleted(
        navController: NavController
    )
    fun onRegistrationCompleted(
        navController: NavController,
        route:String
    )
}