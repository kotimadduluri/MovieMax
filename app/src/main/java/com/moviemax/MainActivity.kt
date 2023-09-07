package com.moviemax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moviemax.view.movie.list.MoviesScreen
import com.moviemax.viewmodel.MoviesScreenViewModel
import com.moviemax.ui.theme.MovieMaxTheme
import com.moviemax.view.movie.Destination
import com.moviemax.view.movie.list.MoviesScreenIntent
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMaxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navControl = rememberNavController()
                    NavHost(navController = navControl, startDestination = Destination.List.route) {
                        composable(Destination.List.route) {
                            val viewModel: MoviesScreenViewModel = getViewModel()
                            val state = viewModel.uiState()
                            MoviesScreen(state) { intent ->
                                when (intent) {
                                    is MoviesScreenIntent.VIEW_DETAILS -> {
                                        //todo
                                    }

                                    is MoviesScreenIntent.REFRESH -> {
                                        viewModel.getMovies()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}