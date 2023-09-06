package com.moviemax.di

import com.moviemax.BuildConfig
import com.network.client.DomainConfiguration
import org.koin.dsl.module

val AppModule = module {
    single {
        DomainConfiguration {
            host = BuildConfig.APP_DOMAIN
        }
    }
}