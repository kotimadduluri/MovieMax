package com.moviemax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moviemax.view.movie.list.MoviesScreen
import com.moviemax.viewmodel.MoviesScreenViewModel
import com.moviemax.ui.theme.MovieMaxTheme
import com.moviemax.view.movie.Destination
import com.moviemax.view.movie.details.MovieDetailsScreen
import com.moviemax.view.movie.details.MovieDetailsScreenIntent
import com.moviemax.view.movie.list.MoviesScreenIntent
import com.moviemax.viewmodel.MovieDetailsScreenViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMaxTheme {
                val navControl = rememberNavController()
                NavHost(navController = navControl, startDestination = Destination.List.route) {
                    composable(Destination.List.route) {
                        val viewModel: MoviesScreenViewModel = getViewModel()
                        val state = viewModel.uiState()
                        MoviesScreen(state) { intent ->
                            when (intent) {
                                is MoviesScreenIntent.VIEW_DETAILS -> {
                                    navControl.navigate(Destination.Details.createRoute(intent.movie.id))
                                }

                                is MoviesScreenIntent.REFRESH -> {
                                    viewModel.getMovies()
                                }
                            }
                        }
                    }

                    composable(
                        Destination.Details.route,
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) { navBackStackEntry ->
                        val movieId = navBackStackEntry.arguments?.getInt("movieId")!!
                        val viewModel: MovieDetailsScreenViewModel = getViewModel()
                        val state = viewModel.uiState()
                        MovieDetailsScreen(
                            movieId,
                            state,
                            navControl
                        ) { intent ->
                            when (intent) {
                                is MovieDetailsScreenIntent.REFRESH -> {
                                    viewModel.getMovieDetails(movieId)
                                }

                                is MovieDetailsScreenIntent.GET_DETAILS -> {
                                    viewModel.getMovieDetails(intent.movieId)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}