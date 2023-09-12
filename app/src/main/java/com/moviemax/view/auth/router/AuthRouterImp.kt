package com.moviemax.view.auth.router

import androidx.navigation.NavController
import com.moviemax.view.auth.AuthModule
import com.moviemax.view.movie.MovieModule

//todo for future routing
class AuthRouterImp : AuthRouter {
    override fun onLoginCompleted(
        navController: NavController,
    ) {
        navController.navigate(MovieModule.ModuleName){
            popUpTo(MovieModule.ModuleName){
                inclusive = true
            }
        }
    }

    override fun onRegistrationCompleted(
        navController: NavController,
        route:String
    ) {
        navController.navigate(route){
            popUpTo(AuthModule.ModuleName){
                inclusive = true
            }
        }
    }
}