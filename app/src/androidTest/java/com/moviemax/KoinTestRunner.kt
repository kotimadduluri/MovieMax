package com.moviemax

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

//custom test runner
class KoinTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, TestMovieMaxApplication::class.java.name, context)
    }
}