package com.network.di

import com.network.util.NetworkReader
import com.network.util.NetworkReaderImp
import org.koin.dsl.module

val NetworkModule = module{
    single<NetworkReader> {
        NetworkReaderImp(get())
    }
}