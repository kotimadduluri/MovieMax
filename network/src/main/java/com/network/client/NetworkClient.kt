package com.network.client

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    lateinit var configuration: DomainConfiguration
    var clientType: NetworkClientType = NetworkClientType.RETROFIT

    fun <T> buildApi(service: Class<T>): T = Retrofit.Builder()
        .baseUrl(getUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build().create(service)

    private fun getUrl() = HttpUrl.Builder().apply {
        host(configuration.host)
        scheme(configuration.scheme)
        if (configuration.port > 0) {
            port(configuration.port)
        }
    }.build()



    private fun getClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if(configuration.logEnabled){ //logs work for debug builds
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(interceptor)
        }
        return httpClient.build()
    }
}

enum class NetworkClientType {
    RETROFIT,
    KOIN
}

fun NetworkClient(block: NetworkClientBuilder.() -> Unit): NetworkClient {
    val builder = NetworkClientBuilder()
    builder.block()
    return builder.build()
}

class NetworkClientBuilder {
    lateinit var configuration: DomainConfiguration
    private var clientType: NetworkClientType = NetworkClientType.RETROFIT

    fun build(): NetworkClient {
        return NetworkClient().apply {
            configuration = this@NetworkClientBuilder.configuration
            clientType = this@NetworkClientBuilder.clientType
        }
    }
}