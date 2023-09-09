package com.moviemax.extentions

import android.content.Context
import com.common.di.CommonModule
import com.moviemax.di.AppModule
import com.network.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Method to initiate DI
 * @param context is application context to bind with koin
 */
fun initKoin(context: Context){
    startKoin {
        androidContext(context)
        modules(listOf(
            AppModule,
            CommonModule,
            NetworkModule
        ))
    }
}