package com.moviemax.util

import android.content.Context
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinTestRule(
    private val appContext: Context,
    private val modules: List<Module>
) : TestWatcher() {
    override fun starting(description: Description) {
        startKoin {
            androidContext(appContext)
            modules(modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}