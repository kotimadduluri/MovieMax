package com.network.client

class DomainConfiguration {
    var host: String = ""
    var port: Int = 0
}

fun DomainConfiguration(block: DomainConfigurationBuilder.()->Unit):DomainConfiguration{
    val builder = DomainConfigurationBuilder()
    builder.block()
    return builder.build()
}

class DomainConfigurationBuilder{
    var host: String    = ""
    private var port: Int       = 0

    fun build(): DomainConfiguration{
        return DomainConfiguration().apply {
            host = this@DomainConfigurationBuilder.host
            port = this@DomainConfigurationBuilder.port
        }
    }
}
