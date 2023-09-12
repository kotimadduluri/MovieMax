package com.moviemax.di

import com.common.di.CommonModule
import com.moviemax.BuildConfig
import com.network.client.DomainConfiguration

val DI_AppModule = CommonModule.apply {
    single {
        DomainConfiguration {
            scheme = "https"
            host = BuildConfig.APP_DOMAIN
            logEnabled = BuildConfig.DEBUG
        }
    }
}