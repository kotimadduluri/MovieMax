package com.network.di

import com.network.reader.NetworkReader
import com.network.reader.NetworkReaderImp
import org.koin.dsl.module

val NetworkModule = module{
    single<NetworkReader> {
        NetworkReaderImp(get())
    }
}