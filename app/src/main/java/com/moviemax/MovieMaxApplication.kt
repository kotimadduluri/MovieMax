package com.moviemax

import android.app.Application
import com.moviemax.extentions.initKoin

class MovieMaxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}