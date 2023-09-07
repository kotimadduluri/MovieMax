package com.moviemax.view.movie

sealed class Destination(val route:String){
    object List : Destination("MovieList")
    object Details : Destination("MovieDetails/{movieId}"){
        fun createRoute(movieId:Int) = "MovieDetails/$movieId"
    }
}
