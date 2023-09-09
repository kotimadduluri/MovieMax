package com.network.client

class DomainConfiguration {
    var scheme: String = ""
    var host: String = ""
    var port: Int = 0
    var logEnabled = false
}

fun DomainConfiguration(block: DomainConfigurationBuilder.() -> Unit): DomainConfiguration {
    val builder = DomainConfigurationBuilder()
    builder.block()
    return builder.build()
}

class DomainConfigurationBuilder {
    var scheme: String = "https"
    var host: String = ""
    private var port: Int = 0
    var logEnabled = false

    fun build(): DomainConfiguration {
        return DomainConfiguration().apply {
            scheme = this@DomainConfigurationBuilder.scheme
            host = this@DomainConfigurationBuilder.host
            port = this@DomainConfigurationBuilder.port
            logEnabled = this@DomainConfigurationBuilder.logEnabled
        }
    }
}
