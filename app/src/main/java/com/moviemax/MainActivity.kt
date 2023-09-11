package com.moviemax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moviemax.view.movie.list.MoviesScreen
import com.moviemax.viewmodel.MoviesScreenViewModel
import com.common.ui.theme.MovieMaxTheme
import com.moviemax.view.movie.Destination
import com.moviemax.view.movie.details.MovieDetailsScreen
import com.moviemax.viewmodel.MovieDetailsScreenViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMaxTheme {
                val navControl = rememberNavController()
                NavHost(navController = navControl, startDestination = Destination.List.route) {
                    composable(Destination.List.route) {
                        val viewModel: MoviesScreenViewModel = koinViewModel()
                        val state = viewModel.uiState().collectAsStateWithLifecycle()
                        MoviesScreen(state) { intent ->
                            viewModel.onAction(intent) { router ->
                                navControl.navigate(router)
                            }
                        }
                    }

                    composable(
                        Destination.Details.route,
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) { navBackStackEntry ->
                        val movieId = navBackStackEntry.arguments?.getInt("movieId")!!
                        val viewModel: MovieDetailsScreenViewModel = koinViewModel()
                        val state = viewModel.uiState().collectAsStateWithLifecycle()
                        MovieDetailsScreen(
                            movieId,
                            state,
                            navControl
                        ) { intent ->
                            viewModel.onAction(intent)
                        }
                    }
                }
            }
        }
    }
}