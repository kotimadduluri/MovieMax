package com.moviemax.extentions

import android.content.Context
import com.moviemax.di.DI_AppModule
import com.moviemax.di.DI_AuthModule
import com.moviemax.di.DI_MovieModule
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
            DI_AppModule,
            DI_MovieModule,
            DI_AuthModule,
            NetworkModule
        ))
    }
}