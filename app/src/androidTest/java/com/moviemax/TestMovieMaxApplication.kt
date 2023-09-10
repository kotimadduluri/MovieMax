package com.moviemax

import android.app.Application
import org.koin.core.context.stopKoin

class TestMovieMaxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //enable if you want inject all dependencies and pass all in place
        //delete custom koin rule to avoid conflicts
        /*startKoin {
            modules(AppModule, TestAppModule)
        }*/
    }


    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}