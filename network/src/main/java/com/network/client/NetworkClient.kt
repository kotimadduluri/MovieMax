package com.network.client

import com.google.gson.Gson
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    lateinit var configuration: DomainConfiguration
    var clientType: NetworkClientType = NetworkClientType.RETROFIT

    fun <T> buildApi(service: Class<T>): T = Retrofit.Builder()
        .baseUrl(getUrl())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build().create(service)

    private fun getUrl() = HttpUrl.Builder().apply {
        host(configuration.host)
        if (configuration.port > 0) {
            port(configuration.port)
        }
    }.build()
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
    private lateinit var configuration: DomainConfiguration
    private var clientType: NetworkClientType = NetworkClientType.RETROFIT

    fun build(): NetworkClient {
        return NetworkClient().apply {
            configuration = this@NetworkClientBuilder.configuration
            clientType = this@NetworkClientBuilder.clientType
        }
    }
}