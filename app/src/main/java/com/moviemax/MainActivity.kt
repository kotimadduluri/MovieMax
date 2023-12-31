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
import androidx.navigation.navigation
import com.moviemax.view.movie.list.MoviesScreen
import com.moviemax.viewmodel.movie.MoviesScreenViewModel
import com.common.ui.theme.MovieMaxTheme
import com.moviemax.view.auth.AuthModule
import com.moviemax.view.auth.LoginScreen
import com.moviemax.view.auth.RegistrationScreen
import com.moviemax.view.movie.MovieModule
import com.moviemax.view.movie.details.MovieDetailsScreen
import com.moviemax.viewmodel.auth.AuthViewModel
import com.moviemax.viewmodel.auth.AuthViewModelIntent
import com.moviemax.viewmodel.movie.MovieDetailsScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMaxTheme {
                val navControl = rememberNavController()
                NavHost(
                    navController = navControl,
                    startDestination = AuthModule.ModuleName
                ) {

                    navigation(
                        startDestination = AuthModule.Login.route,
                        AuthModule.ModuleName
                    ) {
                        composable(AuthModule.Login.route) {
                            val viewModel: AuthViewModel by lazy {
                                getViewModel()
                            }
                            val state = viewModel.uiState().collectAsStateWithLifecycle()
                            val newUser = viewModel.registrationStatus.collectAsStateWithLifecycle()
                            LoginScreen(
                                state,
                                { route ->
                                    navControl.navigate(route) {
                                        popUpTo(MovieModule.ModuleName) {
                                            inclusive = true
                                        }
                                    }
                                },
                                navControl,
                                newUser
                            ) { intent ->
                                viewModel.onAction(intent)
                            }
                        }

                        composable(AuthModule.Registration.route) {
                            val viewModel: AuthViewModel by lazy {
                                getViewModel()
                            }
                            val state = viewModel.uiState().collectAsStateWithLifecycle()
                            RegistrationScreen(
                                state, {
                                    viewModel.onAction(AuthViewModelIntent.Reset)
                                    navControl.navigate(AuthModule.ModuleName) {
                                        popUpTo(AuthModule.ModuleName) {
                                            inclusive = true
                                        }
                                    }
                                }
                            ) { intent ->
                                viewModel.onAction(intent)
                            }
                        }

                    }

                    navigation(
                        startDestination = MovieModule.List.route,
                        MovieModule.ModuleName
                    ) {
                        composable(MovieModule.List.route) {
                            val viewModel: MoviesScreenViewModel = koinViewModel()
                            val state = viewModel.uiState().collectAsStateWithLifecycle()
                            MoviesScreen(state) { intent ->
                                viewModel.onAction(intent) { router ->
                                    navControl.navigate(router)
                                }
                            }
                        }
                        composable(
                            MovieModule.Details.route,
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
}
