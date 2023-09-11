package com.moviemax.view.movie

sealed class MovieModule(val route:String){
    data object List : MovieModule("MovieList")
    data object Details : MovieModule("MovieDetails/{movieId}"){
        fun createRoute(movieId:Int) = "MovieDetails/$movieId"
    }

    companion object{
        const val ModuleName="Movies"
    }
}
